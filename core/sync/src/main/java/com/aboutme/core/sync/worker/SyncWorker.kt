package com.aboutme.core.sync.worker;

import android.content.Context
import android.util.Log.d
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aboutme.core.auth.AuthService
import com.aboutme.core.common.Response
import com.aboutme.core.database.dao.DiaryDataDao
import com.aboutme.core.database.dao.DreamDao
import com.aboutme.core.database.dao.DreamDataDao
import com.aboutme.core.database.dao.MoodDataDao
import com.aboutme.core.database.dao.SleepDataDao
import com.aboutme.core.database.dao.UserDao
import com.aboutme.core.database.entity.DreamEntity
import com.aboutme.core.database.entity.UserEntity
import com.aboutme.core.database.entity.daily.DiaryDataEntity
import com.aboutme.core.database.entity.daily.DreamDataEntity
import com.aboutme.core.database.entity.daily.MoodDataEntity
import com.aboutme.core.database.entity.daily.SleepDataEntity
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.UserData
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
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
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
        diaryDataDao,
        diaryDataSource,
        ::authCall
    ) {
        override fun convertEntityToDto(entity: DiaryDataEntity) = entity.toDto()

        override fun convertDtoToEntity(dto: DiaryDataDto) = dto.toEntity()

    }

    private val moodDataAdapter = object : DailySyncAdapter<MoodDataEntity, MoodDataDto>(
        moodDataDao,
        moodDataSource,
        ::authCall
    ) {
        override fun convertEntityToDto(entity: MoodDataEntity) = entity.toDto()

        override fun convertDtoToEntity(dto: MoodDataDto) = dto.toEntity()

    }

    private val sleepDataAdapter = object : DailySyncAdapter<SleepDataEntity, SleepDataDto>(
        sleepDataDao,
        sleepDataSource,
        ::authCall
    ) {
        override fun convertEntityToDto(entity: SleepDataEntity) = entity.toDto()

        override fun convertDtoToEntity(dto: SleepDataDto) = dto.toEntity()

    }

    private val dreamDataAdapter = object : DailySyncAdapter<DreamDataEntity, DreamDataDto>(
        dreamDataDao,
        dreamDataSource,
        ::authCall
    ) {
        override fun convertEntityToDto(entity: DreamDataEntity) = entity.toDto()

        override fun convertDtoToEntity(dto: DreamDataDto) = dto.toEntity()

    }

    private val dreamAdapter = object : SyncAdapter<DreamEntity, DreamDto, UpdateDreamDto, Long>(
        dreamDao,
        dreamSource,
        ::authCall
    ) {

        override fun convertEntityToDto(entity: DreamEntity) = entity.toDto()

        override fun convertEntityToUpdateDto(entity: DreamEntity) = entity.toUpdateDto()

        override fun convertDtoToEntity(dto: DreamDto) = dto.toEntity()
    }

    override suspend fun doWork(): Result {
        d("SyncWorker", "Started 'doWork'")
        val serverUser = checkAuth() ?: return Result.failure()
        d("SyncWorker", "Got currently authenticated user $serverUser")
        val dbUser = userDao.getAll().first().firstOrNull()
        d("SyncWorker", "Got currently cached user $dbUser")
        syncUser(dbUser, serverUser.user)
        d("SyncWorker", "Finished user syncing. Will now continue")

        syncDiaryData()
        d("SyncWorker", "Finished diary syncing. Will now continue")
        syncSleepData()
        d("SyncWorker", "Finished sleep syncing. Will now continue")
        syncMoodData()
        d("SyncWorker", "Finished mood syncing. Will now continue")
        syncDreamData()
        d("SyncWorker", "Finished dream data syncing. Will now continue")
        syncDreams()
        d("SyncWorker", "Finished dream syncing. Will now return")

        return Result.success()
    }

    private suspend fun syncDiaryData() {
        val serverDiaryDataResponse = authService.saveAuthTransaction {
            diaryDataSource.getAll(it)
        }

        if (serverDiaryDataResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch diary datas from the server: $serverDiaryDataResponse")

        val serverDiaryDatas = serverDiaryDataResponse.data
        d("SyncWorker", "Got diary datas from server: $serverDiaryDatas")
        val dbDiaryDatas = diaryDataDao.getAll().first()
        d("SyncWorker", "Got diary datas from local cache: $dbDiaryDatas")

        val dates = mutableListOf<LocalDate>()

        for (dto in serverDiaryDatas) {
            d("SyncWorker", "Currently doing data from server $dto")
            val entity = dbDiaryDatas.find { it.date == dto.date }
            d("SyncWorker", "Got equivalent entity from local cache $entity")
            diaryDataAdapter.sync(entity, dto, dto.date)
            dates.add(dto.date)
        }
        for (entity in dbDiaryDatas) {
            if (entity.date in dates) continue
            val dto = serverDiaryDatas.find { it.date == entity.date }
            diaryDataAdapter.sync(entity, dto, entity.date)
        }
    }

    private suspend fun syncSleepData() {
        val serverSleepDataResponse = authService.saveAuthTransaction {
            sleepDataSource.getAll(it)
        }

        if (serverSleepDataResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch sleep datas from the server: $serverSleepDataResponse")

        val serverSleepDatas = serverSleepDataResponse.data
        val dbSleepDatas = sleepDataDao.getAll().first()

        val dates = mutableListOf<LocalDate>()

        for (dto in serverSleepDatas) {
            val entity = dbSleepDatas.find { it.date == dto.date }
            sleepDataAdapter.sync(entity, dto, dto.date)
            dates.add(dto.date)
        }
        for (entity in dbSleepDatas) {
            if (entity.date in dates) continue
            val dto = serverSleepDatas.find { it.date == entity.date }
            sleepDataAdapter.sync(entity, dto, entity.date)
        }
    }

    private suspend fun syncDreamData() {
        val serverDreamDataResponse = authService.saveAuthTransaction {
            dreamDataSource.getAll(it)
        }

        if (serverDreamDataResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch dream datas from the server: $serverDreamDataResponse")

        val serverDreamDatas = serverDreamDataResponse.data
        val dbDreamDatas = dreamDataDao.getAllWithDreams().first().map { it.dreamData }

        val dates = mutableListOf<LocalDate>()

        for (dto in serverDreamDatas) {
            val entity = dbDreamDatas.find { it.date == dto.date }
            dreamDataAdapter.sync(entity, dto, dto.date)
            dates.add(dto.date)
        }
        for (entity in dbDreamDatas) {
            if (entity.date in dates) continue
            val dto = serverDreamDatas.find { it.date == entity.date }
            dreamDataAdapter.sync(entity, dto, entity.date)
        }
    }

    private suspend fun syncMoodData() {
        val serverMoodDataResponse = authService.saveAuthTransaction {
            moodDataSource.getAll(it)
        }

        if (serverMoodDataResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch mood datas from the server: $serverMoodDataResponse")

        val serverMoodDatas = serverMoodDataResponse.data
        val dbMoodDatas = moodDataDao.getAll().first()

        val dates = mutableListOf<LocalDate>()

        for (dto in serverMoodDatas) {
            val entity = dbMoodDatas.find { it.date == dto.date }
            moodDataAdapter.sync(entity, dto, dto.date)
            dates.add(dto.date)
        }
        for (entity in dbMoodDatas) {
            if (entity.date in dates) continue
            val dto = serverMoodDatas.find { it.date == entity.date }
            moodDataAdapter.sync(entity, dto, entity.date)
        }
    }

    private suspend fun syncDreams() {
        val serverDreamsResponse = authService.saveAuthTransaction {
            dreamSource.getAll(it)
        }

        if (serverDreamsResponse !is Response.Success) throw IllegalStateException("An error occured trying to fetch dreams from the server: $serverDreamsResponse")

        val serverDreams = serverDreamsResponse.data
        val dbDreams = dreamDao.getAll().first()

        val dates = mutableListOf<LocalDate>()

        for (dto in serverDreams) {
            val entity = dbDreams.find { it.date == dto.date }
            dreamAdapter.sync(entity, dto, dto.id)
            dates.add(dto.date)
        }
        for (entity in dbDreams) {
            if (entity.date in dates) continue
            val dto = serverDreams.find { it.date == entity.date }
            dreamAdapter.sync(entity, dto, entity.id!!)
        }
    }

    private suspend fun syncUser(dbUser: UserEntity?, serverUser: UserData) {
        val notSameEmail = dbUser != null && (dbUser.email != serverUser.email)
        val useLocal = dbUser != null && (dbUser.updatedAt >= serverUser.updatedAt)
        d("SyncWorker", if(notSameEmail) "The emails weren't same, will throw" else if(useLocal) "Will use the local user" else "Will use the server user")
        if (notSameEmail) throw IllegalStateException("The server user and locally cached user do not have the same email addresses.")

        if (useLocal) {
            authCall {
                userSource.update(dbUser!!.email, dbUser.toUpdateDto(), it)
            }
        } else if(dbUser == null) {
            userDao.insert(serverUser.toEntity())
        } else {
            userDao.update(serverUser.toEntity())
        }
    }

    private suspend fun checkAuth(): AuthUser? {
        val result = authService.refresh()
        return (result as? Response.Success)?.data
    }

}