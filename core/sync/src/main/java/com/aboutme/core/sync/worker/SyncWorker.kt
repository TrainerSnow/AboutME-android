package com.aboutme.core.sync.worker;

import android.content.Context
import android.util.Log.d
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
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
import com.aboutme.core.model.Response
import com.aboutme.core.model.daily.DailyData
import com.aboutme.core.model.daily.DailyDataInfo
import com.aboutme.core.model.daily.data.DiaryData
import com.aboutme.core.model.daily.data.DreamData
import com.aboutme.core.model.daily.data.MoodData
import com.aboutme.core.model.daily.data.SleepData
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.UserData
import com.aboutme.core.secret.TokenRepository
import com.aboutme.network.source.DailyDataSource
import com.aboutme.network.source.UserNetworkSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@Suppress("MemberVisibilityCanBePrivate")
@HiltWorker
internal class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,

    val tokenRepository: TokenRepository,

    val authSource: UserNetworkSource,

    val dailyDataSource: DailyDataSource,

    val dreamDao: DreamDao,
    val userDao: UserDao,

    val dreamDataDao: DreamDataDao,
    val diaryDataDao: DiaryDataDao,
    val moodDataDao: MoodDataDao,
    val sleepDataDao: SleepDataDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val serverUser = checkAuth() ?: return Result.failure()
        d("SyncWorker", "Received server user '$serverUser'")
        val dbUser = userDao.getByEmail(serverUser.user.email).first()
        d("SyncWorker", "Received database user '$dbUser'")

        if (dbUser == null) {
            d("SyncWorker", "Will take all data from server")
            takeAllServerData(serverUser.user)
        } else {
            d("SyncWorker", "Will take data dynamically")
            takeDynamicData(serverUser.user, dbUser)
        }


        return Result.success()
    }

    private suspend fun checkAuth(): AuthUser? {
        val result = authSource.refresh(tokenRepository.getRefreshToken() ?: "")
        if(result is Response.Success) {
            tokenRepository.setRefreshToken(result.data.authData.refreshToken)
        }
        return (result as? Response.Success)?.data
    }

    //TODO: Also sync persons and relations and other stuff here
    private suspend fun takeDynamicData(serverUser: UserData, dbUser: UserEntity) {
        if (serverUser.updatedAt >= dbUser.updatedAt) {
            d("SyncWorker", "Will use server user")
            useServerUser(serverUser)
        } else {
            useDbUser(dbUser)
            d("SyncWorker", "Will use db user")
        }

        val serverDailyDatas = dailyDataSource.getAll(tokenRepository.getToken() ?: "")
        if (serverDailyDatas !is Response.Success) return

        serverDailyDatas.data.forEach { dailyData ->
            val serverDiaryData = dailyData.diaryData
            val dbDiaryData = diaryDataDao.getByDate(serverDiaryData.date).first()
            decideDiaryData(serverDiaryData, dbDiaryData)

            val serverDreamData = dailyData.dreamData
            val dbDreamData = dreamDataDao.getWithDreamsByDate(serverDreamData.date).first()
            decideDreamData(serverDreamData, dbDreamData?.dreamData)

            val serverSleepData = dailyData.sleepData
            val dbSleepData = sleepDataDao.getByDate(serverSleepData.date).first()
            decideSleepData(serverSleepData, dbSleepData)

            val serverMoodData = dailyData.moodData
            val dbMoodData = moodDataDao.getByDate(serverMoodData.date).first()
            decideMoodData(serverMoodData, dbMoodData)
        }
    }

    private suspend fun decideDiaryData(
        serverDiaryData: DailyDataInfo<DiaryData>,
        dbDiaryData: DiaryDataEntity?
    ) {
        if (serverDiaryData.data == null && dbDiaryData == null) return
        if (dbDiaryData != null && serverDiaryData.data == null) {
            useDbDiaryData(dbDiaryData)
        } else if (dbDiaryData == null) {
            useServerDiaryData(serverDiaryData)
        } else {
            if (serverDiaryData.date >= dbDiaryData.date) useServerDiaryData(serverDiaryData)
            else useDbDiaryData(dbDiaryData)
        }
    }

    private suspend fun decideMoodData(
        serverMoodData: DailyDataInfo<MoodData>,
        dbMoodData: MoodDataEntity?
    ) {
        if (serverMoodData.data == null && dbMoodData == null) return
        if (dbMoodData != null && serverMoodData.data == null) {
            useDbMoodData(dbMoodData)
        } else if (dbMoodData == null) {
            useServerMoodData(serverMoodData)
        } else {
            if (serverMoodData.date >= dbMoodData.date) useServerMoodData(serverMoodData)
            else useDbMoodData(dbMoodData)
        }
    }

    private suspend fun decideDreamData(
        serverDreamData: DailyDataInfo<DreamData>,
        dbDreamData: DreamDataEntity?
    ) {
        if (serverDreamData.data == null && dbDreamData == null) return
        if (dbDreamData != null && serverDreamData.data == null) {
            useDbDreamData(dbDreamData)
        } else if (dbDreamData == null) {
            useServerDreamData(serverDreamData)
        } else {
            if (serverDreamData.date >= dbDreamData.date) useServerDreamData(serverDreamData)
            else useDbDreamData(dbDreamData)
        }
    }

    private suspend fun decideSleepData(
        serverSleepData: DailyDataInfo<SleepData>,
        dbSleepData: SleepDataEntity?
    ) {
        if (serverSleepData.data == null && dbSleepData == null) return
        if (dbSleepData != null && serverSleepData.data == null) {
            useDbSleepData(dbSleepData)
        } else if (dbSleepData == null) {
            useServerSleepData(serverSleepData)
        } else {
            if (serverSleepData.date >= dbSleepData.date) useServerSleepData(serverSleepData)
            else useDbSleepData(dbSleepData)
        }
    }

    //TODO: Also sync persons and relations and other stuff here
    private suspend fun takeAllServerData(serverUser: UserData) {
        //We empty local database
        userDao.deleteAll()
        dreamDao.deleteAll()
        dreamDataDao.deleteAll()
        moodDataDao.deleteAll()
        sleepDataDao.deleteAll()
        diaryDataDao.deleteAll()

        d("SyncWorker", "Emptied local database")

        //Save new user
        userDao.insert(
            UserEntity(
                serverUser.email,
                serverUser.nameInfo,
                serverUser.createdAt,
                serverUser.updatedAt
            )
        )
        d("SyncWorker", "Inserted new user")


        val serverDailyDatas = dailyDataSource.getAll(tokenRepository.getToken() ?: "")

        if (serverDailyDatas is Response.Success) {
            val dailyDatas = serverDailyDatas.data
            d("SyncWorker", "Got daily datas: \n${dailyDatas.joinToString("\n")}")

            dailyDatas.forEach {
                useServerDailyData(it)
            }
        }
    }


    /*
    Local cache -> Server
     */

    private suspend fun useDbUser(userData: UserEntity) {
        authSource.updateUser(userData.nameInfo, tokenRepository.getToken() ?: "")
    }

    private suspend fun useDbDiaryData(diaryData: DiaryDataEntity) {
        TODO()
    }

    private suspend fun useDbSleepData(sleepData: SleepDataEntity) {
        TODO()
    }

    private suspend fun useDbMoodData(moodData: MoodDataEntity) {
        TODO()
    }

    private suspend fun useDbDreamData(dreamData: DreamDataEntity) {
        TODO()
    }


    /*
    Server -> Local cache
     */

    private suspend fun useServerUser(userData: UserData) {
        userDao.update(
            userData.run {
                UserEntity(email, nameInfo, createdAt, updatedAt)
            }
        )
    }

    private suspend fun useServerDailyData(dailyData: DailyData) {
        d("SyncWorker", "Taking server version for daily data $dailyData")
        useServerDiaryData(dailyData.diaryData)
        useServerSleepData(dailyData.sleepData)
        useServerDreamData(dailyData.dreamData)
        useServerMoodData(dailyData.moodData)
    }

    private suspend fun useServerDiaryData(diaryData: DailyDataInfo<DiaryData>) {
        if (diaryData.data == null) return
        val entity = diaryData.run {
            DiaryDataEntity(date, data!!.content, data!!.createdAt, data!!.updatedAt)
        }
        diaryDataDao.insert(entity)
    }

    private suspend fun useServerMoodData(moodData: DailyDataInfo<MoodData>) {
        if (moodData.data == null) return
        val entity = moodData.run {
            if (data is MoodData.VaryingMoodData) {
                val cast = data as MoodData.VaryingMoodData
                MoodDataEntity(
                    date,
                    cast.averageMood,
                    cast.morning,
                    cast.noon,
                    cast.evening,
                    cast.createdAt,
                    cast.updatedAt
                )
            } else {
                val cast = data as MoodData.ConstantMoodData
                MoodDataEntity(
                    date,
                    cast.mood,
                    null,
                    null,
                    null,
                    cast.createdAt,
                    cast.updatedAt
                )
            }
        }
        moodDataDao.insert(entity)
    }

    private suspend fun useServerSleepData(sleepData: DailyDataInfo<SleepData>) {
        if (sleepData.data == null) return
        val entity = sleepData.run {
            SleepDataEntity(
                date,
                data!!.hoursSlept,
                data!!.hoursAim,
                data!!.createdAt,
                data!!.updatedAt
            )
        }
        sleepDataDao.insert(entity)
    }

    private suspend fun useServerDreamData(dreamData: DailyDataInfo<DreamData>) {
        if (dreamData.data == null) return

        val dreams = dreamData.data!!.dreams
        dreams.forEach { dream ->
            val entity = dream.run {
                DreamEntity(
                    id,
                    dreamData.date,
                    content,
                    annotation,
                    mood,
                    clearness,
                    createdAt,
                    updatedAt
                )
            }
            dreamDao.insert(entity)
        }

        val entity = dreamData.run {
            DreamDataEntity(date, data!!.createdAt, data!!.updatedAt)
        }
        dreamDataDao.insert(entity)
    }

}



































