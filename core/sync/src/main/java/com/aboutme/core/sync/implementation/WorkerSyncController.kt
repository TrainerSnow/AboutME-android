package com.aboutme.core.sync.implementation;

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aboutme.core.sync.SyncController
import com.aboutme.core.sync.worker.SyncWorker
import java.time.Duration

internal class WorkerSyncController(
    private val workManager: WorkManager
) : SyncController {

    override fun syncNow() {
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .addTag(SYNC_WORKER_TAG)
            .build()

        workManager.enqueue(request)
    }

    //TODO: In user preferences, allow to change for only wifi
    override fun schedulePeriodically(hours: Long) {
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

    override fun unscheduleAll() {
        workManager.cancelAllWorkByTag(SYNC_WORKER_TAG)
    }

    companion object {

        internal const val SYNC_WORKER_TAG = "com.aboutme.sync.SyncWorker"

        private const val SYNC_WORKER_NAME_REPEATING = "com.aboutme.sync.SyncWorker.periodic"

    }

}