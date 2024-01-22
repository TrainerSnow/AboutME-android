package com.aboutme.core.sync.implementation;

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aboutme.core.sync.SyncController
import com.aboutme.core.sync.worker.SyncWorker
import java.time.Duration
import java.util.UUID
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

internal class WorkerSyncController(
    private val workManager: WorkManager
) : SyncController {

    override suspend fun syncNow() {
        val id = UUID.randomUUID()

        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .addTag(SYNC_WORKER_TAG)
            .setId(id)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        workManager.enqueue(request)
    }

    override suspend fun schedulePeriodically(minutes: Long) {
        val id = UUID.randomUUID()
        require(minutes > 15)

        val request = PeriodicWorkRequestBuilder<SyncWorker>(Duration.ofMinutes(minutes))
            .addTag(SYNC_WORKER_TAG)
            .setId(id)
            .setInitialDelay((10L).toDuration(DurationUnit.SECONDS).toJavaDuration())
            .setConstraints(Constraints(requiredNetworkType = NetworkType.NOT_ROAMING))
            .build()

        workManager.enqueueUniquePeriodicWork(
            SYNC_WORKER_NAME_REPEATING,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    override suspend fun unscheduleAll() {
        workManager.cancelAllWork()
    }

    companion object {

        internal const val SYNC_WORKER_TAG = "com.aboutme.sync.SyncWorker"

        private const val SYNC_WORKER_NAME_REPEATING = "com.aboutme.sync.SyncWorker.periodic"

    }

}