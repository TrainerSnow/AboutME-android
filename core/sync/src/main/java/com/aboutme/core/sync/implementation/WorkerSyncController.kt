package com.aboutme.core.sync.implementation;

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.aboutme.core.database.dao.SyncStatusDao
import com.aboutme.core.database.entity.SyncResultData
import com.aboutme.core.database.entity.SyncStatusEntity
import com.aboutme.core.sync.SyncController
import com.aboutme.core.sync.worker.SyncErrorDto
import com.aboutme.core.sync.worker.SyncSuccessDto
import com.aboutme.core.sync.worker.SyncWorker
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.collectLatest
import java.time.Duration
import java.time.Instant
import java.util.UUID

internal class WorkerSyncController(
    private val workManager: WorkManager,
    private val syncStatusDao: SyncStatusDao
) : SyncController {

    override suspend fun syncNow() {
        val id = UUID.randomUUID()

        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .addTag(SYNC_WORKER_TAG)
            .setId(id)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        workManager.enqueue(request)

        workManager.getWorkInfoByIdFlow(id).collectLatest {
            if (it.state == WorkInfo.State.FAILED) {
                val json =
                    it.outputData.getString(SyncWorker.OUTPUT_DATA_KEY) ?: return@collectLatest
                saveAuthError(json)
            }
            if (it.state == WorkInfo.State.SUCCEEDED) {
                val json =
                    it.outputData.getString(SyncWorker.OUTPUT_DATA_KEY) ?: return@collectLatest
                saveSuccess(json)
            }
        }
    }

    //TODO: In user preferences, allow to change for only wifi
    override suspend fun schedulePeriodically(hours: Long) {
        val request = PeriodicWorkRequestBuilder<SyncWorker>(Duration.ofHours(hours))
            .addTag(SYNC_WORKER_TAG)
            .setConstraints(Constraints(requiredNetworkType = NetworkType.NOT_ROAMING))
            .build()

        workManager.enqueueUniquePeriodicWork(
            SYNC_WORKER_NAME_REPEATING,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    override suspend fun unscheduleAll() {
        workManager.cancelAllWorkByTag(SYNC_WORKER_TAG)
    }

    private suspend fun saveAuthError(json: String) {
        val dto = try {
            Gson().fromJson(json, SyncErrorDto::class.java)
        } catch (e: JsonSyntaxException) {
            return
        }

        val entity = SyncStatusEntity(Instant.ofEpochSecond(dto.start), Instant.ofEpochSecond(dto.end), 3)
        syncStatusDao.insert(entity)
    }

    private suspend fun saveSuccess(json: String) {
        val dto = try {
            Gson().fromJson(json, SyncSuccessDto::class.java)
        } catch (e: JsonSyntaxException) {
            return
        }

        val statusEntity = SyncStatusEntity(Instant.ofEpochSecond(dto.start), Instant.ofEpochSecond(dto.end), 1)
        val dataEntity = SyncResultData(
            null,
            statusEntity.startedAt,
            dto.diaryDataTraffic,
            dto.sleepDataTraffic,
            dto.moodDataTraffic,
            dto.dreamDataTraffic,
            dto.dreamTraffic,
            dto.personsTraffic,
            dto.relationsTraffic,
            dto.userTraffic
        )

        syncStatusDao.insert(statusEntity)
        syncStatusDao.insert(dataEntity)
    }

    companion object {

        internal const val SYNC_WORKER_TAG = "com.aboutme.sync.SyncWorker"

        private const val SYNC_WORKER_NAME_REPEATING = "com.aboutme.sync.SyncWorker.periodic"

    }

}