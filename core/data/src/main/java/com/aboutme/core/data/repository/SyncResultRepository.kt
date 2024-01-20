package com.aboutme.core.data.repository

import com.aboutme.core.model.sync.SyncResult
import kotlinx.coroutines.flow.Flow
import java.time.Instant

/**
 * Gives access to the stored results of sync actions
 */
interface SyncResultRepository {

    /**
     * Gets all the SyncResults saved
     */
    fun getAll(): Flow<List<SyncResult>>

    /**
     * Gets the SyncResult that was started at the particular time
     */
    fun getByStarted(started: Instant): Flow<SyncResult?>

}