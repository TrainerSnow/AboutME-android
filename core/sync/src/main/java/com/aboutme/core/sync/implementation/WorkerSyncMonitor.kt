package com.aboutme.core.sync.implementation;

import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.aboutme.core.sync.SyncMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class WorkerSyncMonitor(
    private val workManager: WorkManager
) : SyncMonitor {
    override fun isSyncing(): Flow<Boolean> =
        workManager
            .getWorkInfosByTagFlow(WorkerSyncController.SYNC_WORKER_TAG)
            .map {
                it
                    .firstOrNull()
                    ?.state == WorkInfo.State.RUNNING
            }

}