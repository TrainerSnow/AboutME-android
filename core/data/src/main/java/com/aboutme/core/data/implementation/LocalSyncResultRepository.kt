package com.aboutme.core.data.implementation;

import com.aboutme.core.data.mapping.toSyncResult
import com.aboutme.core.data.repository.SyncResultRepository
import com.aboutme.core.database.dao.SyncStatusDao
import com.aboutme.core.model.sync.SyncResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant

internal class LocalSyncResultRepository(
    private val syncResultDao: SyncStatusDao
) : SyncResultRepository {

    override fun getAll(): Flow<List<SyncResult>> = syncResultDao
        .getAllWithResult()
        .map { statuses ->
            statuses.map {
                (it.syncStatus to it.syncResult).toSyncResult()
            }
        }

    override fun getByStarted(started: Instant) = syncResultDao
        .getAllWithResult()
        .map {
            val found = it.find {
                it.syncStatus.startedAt == started
            }
            if (found == null) null
            else (found.syncStatus to found.syncResult).toSyncResult()
        }


}