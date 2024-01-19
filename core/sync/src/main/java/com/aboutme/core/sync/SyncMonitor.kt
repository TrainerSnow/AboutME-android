package com.aboutme.core.sync

import kotlinx.coroutines.flow.Flow

/**
 * Exposes the state of current sync workers
 */
interface SyncMonitor {

    /**
     * Whether the app is currently syncing
     */
    fun isSyncing(): Flow<Boolean>

}