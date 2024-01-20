package com.aboutme.core.sync.worker;

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.aboutme.core.auth.AuthService
import com.aboutme.core.cache.dao.DiaryDataDao
import com.aboutme.core.cache.dao.DreamDao
import com.aboutme.core.cache.dao.DreamDataDao
import com.aboutme.core.cache.dao.MoodDataDao
import com.aboutme.core.cache.dao.SleepDataDao
import com.aboutme.core.cache.dao.UserDao
import com.aboutme.core.cache.entity.DreamEntity
import com.aboutme.core.cache.entity.UserEntity
import com.aboutme.core.cache.entity.daily.DiaryDataEntity
import com.aboutme.core.cache.entity.daily.DreamDataEntity
import com.aboutme.core.cache.entity.daily.MoodDataEntity
import com.aboutme.core.cache.entity.daily.SleepDataEntity
import com.aboutme.core.common.Response
import com.aboutme.core.database.entity.model.SyncTraffic
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.UserData
import com.aboutme.core.sync.adapter.AdapterResult
import com.aboutme.core.sync.adapter.DailySyncAdapter
import com.aboutme.core.sync.adapter.SyncAdapter
import com.aboutme.core.sync.toDto
import com.aboutme.core.sync.toEntity
import com.aboutme.core.sync.toUpdateDto
import com.aboutme.network.dto.DreamDto
import com.aboutme.network.dto.daily.DiaryDataDto
import com.aboutme.network.dto.daily.DreamDataDto
import com.aboutme.network.dto.daily.MoodDataDto
import com.aboutme.network.dto.daily.SleepDataDto
import com.aboutme.network.dto.update.UpdateDreamDto
import com.aboutme.network.source.DreamSource
import com.aboutme.network.source.UserNetworkSource
import com.aboutme.network.source.daily.DiaryDataSource
import com.aboutme.network.source.daily.DreamDataSource
import com.aboutme.network.source.daily.MoodDataSource
import com.aboutme.network.source.daily.SleepDataSource
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.time.Instant
import java.time.LocalDate

@Suppress("MemberVisibilityCanBePrivate")
@HiltWorker
internal class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,

    val authService: AuthService,

    val diaryDataSource: DiaryDataSource,
    val dreamDataSource: DreamDataSource,
    val moodDataSource: MoodDataSource,
    val sleepDataSource: SleepDataSource,
    val userSource: UserNetworkSource,
    val dreamSource: DreamSource,

    val dreamDao: DreamDao,
    val userDao: UserDao,
    val dreamDataDao: DreamDataDao,
    val diaryDataDao: DiaryDataDao,
    val moodDataDao: MoodDataDao,
    val sleepDataDao: SleepDataDao
) : CoroutineWorker(context, params) {

    private suspend fun authCall(block: suspend (token: String) -> Unit) {
        authService.saveAuthTransaction<Unit> {
            block(it)
            return@saveAuthTransaction Response.Loading()
        }
    }

    private val diaryDataAdapter = object : DailySyncAdapter<DiaryDataEntity, DiaryDataDto>(
        diaryDataDao, diaryDataSource, ::authCall
    ) {
        override fun convertEntityToDto(entity: DiaryDataEntity) = entity.toDto()

        override fun convertDtoToEntity(dto: DiaryDataDto) = dto.toEntity()

    }

    private val moodDataAdapter = object : DailySyncAdapter<MoodDataEntity, MoodDataDto>(
        moodDataDao, moodDataSource, ::authCall
    ) {
        override fun convertEntityToDto(entity: MoodDataEntity) = entity.toDto()

        override fun convertDtoToEntity(dto: MoodDataDto) = dto.toEntity()

    }

    private val sleepDataAdapter = object : DailySyncAdapter<SleepDataEntity, SleepDataDto>(
        sleepDataDao, sleepDataSource, ::authCall
    ) {
        override fun convertEntityToDto(entity: SleepDataEntity) = entity.toDto()

        override fun convertDtoToEntity(dto: SleepDataDto) = dto.toEntity()

    }

    private val dreamDataAdapter = object : DailySyncAdapter<DreamDataEntity, DreamDataDto>(
        dreamDataDao, dreamDataSource, ::authCall
    ) {
        override fun convertEntityToDto(entity: DreamDataEntity) = entity.toDto()

        override fun convertDtoToEntity(dto: DreamDataDto) = dto.toEntity()

    }

    private val dreamAdapter = object : SyncAdapter<DreamEntity, DreamDto, UpdateDreamDto, Long>(
        dreamDao, dreamSource, ::authCall
    ) {

        override fun convertEntityToDto(entity: DreamEntity) = entity.toDto()

        override fun convertEntityToUpdateDto(entity: DreamEntity) = entity.toUpdateDto()

        override fun convertDtoToEntity(dto: DreamDto) = dto.toEntity()
    }

    private fun createSyncFailure(start: Instant): Result {
        val dto = SyncErrorDto(start, Instant.now())
        val json = Gson().toJson(dto)
        val data = workDataOf(OUTPUT_DATA_KEY to json)
        return Result.failure(data)
    }

    private fun createSuccess(
        start: Instant,
        userTraffic: SyncTraffic,
        diaryTraffic: SyncTraffic,
        sleepTraffic: SyncTraffic,
        moodTraffic: SyncTraffic,
        dreamDataTraffic: SyncTraffic,
        dreamTraffic: SyncTraffic
    ): Result {
        val dto = SyncSuccessDto(
            start,
            Instant.now(),
            diaryDataTraffic = diaryTraffic,
            sleepDataTraffic = sleepTraffic,
            moodDataTraffic = moodTraffic,
            dreamDataTraffic = dreamDataTraffic,
            dreamTraffic = dreamTraffic,
            personsTraffic = SyncTraffic(),
            relationsTraffic = SyncTraffic(),
            userTraffic = userTraffic
        )
        val json = Gson().toJson(dto)
        val data = workDataOf(OUTPUT_DATA_KEY to json)
        return Result.success(data)
    }

    override suspend fun doWork(): Result {
        val start = Instant.now()
        val serverUser = checkAuth() ?: return createSyncFailure(start)
        val dbUser = userDao.getAll().first().firstOrNull()

        val userTraffic = syncUser(dbUser, serverUser.user)

        val diaryTraffic = syncDiaryData()
        val sleepTraffic = syncSleepData()
        val moodTraffic = syncMoodData()
        val dreamDataTraffic = syncDreamData()
        val dreamTraffic = syncDreams()

        return createSuccess(
            start,
            userTraffic,
            diaryTraffic,
            sleepTraffic,
            moodTraffic,
            dreamDataTraffic,
            dreamTraffic
        )
    }

    private suspend fun syncDiaryData(): SyncTraffic {
        val results = mutableListOf<AdapterResult>()

        val serverDiaryDataResponse = authService.saveAuthTransaction {
            diaryDataSource.getAll(it)
        }

        if (serverDiaryDataResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch diary datas from the server: $serverDiaryDataResponse")

        val serverDiaryDatas = serverDiaryDataResponse.data
        val dbDiaryDatas = diaryDataDao.getAll().first()

        val dates = mutableListOf<LocalDate>()

        for (dto in serverDiaryDatas) {
            val entity = dbDiaryDatas.find { it.date == dto.date }
            diaryDataAdapter.sync(entity, dto, dto.date).let(results::add)
            dates.add(dto.date)
        }
        for (entity in dbDiaryDatas) {
            if (entity.date in dates) continue
            val dto = serverDiaryDatas.find { it.date == entity.date }
            diaryDataAdapter.sync(entity, dto, entity.date).let(results::add)
        }

        return results.toSyncTraffic()
    }

    private suspend fun syncSleepData(): SyncTraffic {
        val results = mutableListOf<AdapterResult>()

        val serverSleepDataResponse = authService.saveAuthTransaction {
            sleepDataSource.getAll(it)
        }

        if (serverSleepDataResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch sleep datas from the server: $serverSleepDataResponse")

        val serverSleepDatas = serverSleepDataResponse.data
        val dbSleepDatas = sleepDataDao.getAll().first()

        val dates = mutableListOf<LocalDate>()

        for (dto in serverSleepDatas) {
            val entity = dbSleepDatas.find { it.date == dto.date }
            sleepDataAdapter.sync(entity, dto, dto.date).let(results::add)
            dates.add(dto.date)
        }
        for (entity in dbSleepDatas) {
            if (entity.date in dates) continue
            val dto = serverSleepDatas.find { it.date == entity.date }
            sleepDataAdapter.sync(entity, dto, entity.date).let(results::add)
        }

        return results.toSyncTraffic()
    }

    private suspend fun syncDreamData(): SyncTraffic {
        val results = mutableListOf<AdapterResult>()

        val serverDreamDataResponse = authService.saveAuthTransaction {
            dreamDataSource.getAll(it)
        }

        if (serverDreamDataResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch dream datas from the server: $serverDreamDataResponse")

        val serverDreamDatas = serverDreamDataResponse.data
        val dbDreamDatas = dreamDataDao.getAllWithDreams().first().map { it.dreamData }

        val dates = mutableListOf<LocalDate>()

        for (dto in serverDreamDatas) {
            val entity = dbDreamDatas.find { it.date == dto.date }
            dreamDataAdapter.sync(entity, dto, dto.date).let(results::add)
            dates.add(dto.date)
        }
        for (entity in dbDreamDatas) {
            if (entity.date in dates) continue
            val dto = serverDreamDatas.find { it.date == entity.date }
            dreamDataAdapter.sync(entity, dto, entity.date).let(results::add)
        }

        return results.toSyncTraffic()
    }

    private suspend fun syncMoodData(): SyncTraffic {
        val results = mutableListOf<AdapterResult>()

        val serverMoodDataResponse = authService.saveAuthTransaction {
            moodDataSource.getAll(it)
        }

        if (serverMoodDataResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch mood datas from the server: $serverMoodDataResponse")

        val serverMoodDatas = serverMoodDataResponse.data
        val dbMoodDatas = moodDataDao.getAll().first()

        val dates = mutableListOf<LocalDate>()

        for (dto in serverMoodDatas) {
            val entity = dbMoodDatas.find { it.date == dto.date }
            moodDataAdapter.sync(entity, dto, dto.date).let(results::add)
            dates.add(dto.date)
        }
        for (entity in dbMoodDatas) {
            if (entity.date in dates) continue
            val dto = serverMoodDatas.find { it.date == entity.date }
            moodDataAdapter.sync(entity, dto, entity.date).let(results::add)
        }

        return results.toSyncTraffic()
    }

    private suspend fun syncDreams(): SyncTraffic {
        val results = mutableListOf<AdapterResult>()

        val serverDreamsResponse = authService.saveAuthTransaction {
            dreamSource.getAll(it)
        }

        if (serverDreamsResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch dreams from the server: $serverDreamsResponse")

        val serverDreams = serverDreamsResponse.data
        val dbDreams = dreamDao.getAll().first()

        val dates = mutableListOf<LocalDate>()

        for (dto in serverDreams) {
            val entity = dbDreams.find { it.date == dto.date }
            dreamAdapter.sync(entity, dto, dto.id).let(results::add)
            dates.add(dto.date)
        }
        for (entity in dbDreams) {
            if (entity.date in dates) continue
            val dto = serverDreams.find { it.date == entity.date }
            dreamAdapter.sync(entity, dto, entity.id!!).let(results::add)
        }

        return results.toSyncTraffic()
    }

    private suspend fun syncUser(dbUser: UserEntity?, serverUser: UserData): SyncTraffic {
        val notSameEmail = dbUser != null && (dbUser.email != serverUser.email)
        val useLocal = dbUser != null && (dbUser.updatedAt >= serverUser.updatedAt)
        if (notSameEmail) throw IllegalStateException("The server user and locally cached user do not have the same email addresses.")

        return if (useLocal) {
            authCall {
                userSource.update(dbUser!!.email, dbUser.toUpdateDto(), it)
            }
            SyncTraffic(serverUpdated = 1)
        } else if (dbUser == null) {
            userDao.insert(serverUser.toEntity())
            SyncTraffic(localAdded = 1)
        } else {
            userDao.update(serverUser.toEntity())
            SyncTraffic(localUpdated = 1)
        }
    }

    private suspend fun checkAuth(): AuthUser? {
        val result = authService.refresh()
        return (result as? Response.Success)?.data
    }

    private fun List<AdapterResult>.toSyncTraffic() = groupingBy { it }.eachCount().run {
        SyncTraffic(
            serverAdded = get(AdapterResult.AddedServer) ?: 0,
            localAdded = get(AdapterResult.AddedLocal) ?: 0,
            serverUpdated = get(AdapterResult.UpdatedServer) ?: 0,
            localUpdated = get(AdapterResult.UpdatedLocal) ?: 0,
            serverDeleted = get(AdapterResult.DeletedServer) ?: 0,
            localDeleted = get(AdapterResult.DeletedLocal) ?: 0
        )
    }

    companion object {

        internal const val OUTPUT_DATA_KEY = "com.aboutme.core.sync.SyncWorker.result"

    }

}