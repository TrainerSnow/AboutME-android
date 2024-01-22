package com.aboutme.core.sync

/**
 * Exposes functionality related to the sync worker
 */
interface SyncController {

    /**
     * Schedules a sync right now
     */
    suspend fun syncNow()

    /**
     * Schedules a synchronization periodically
     *
     * @param minutes The period in minutes
     */
    suspend fun schedulePeriodically(minutes: Long)

    /**
     * Unschedules all currently scheduled
     */
    suspend fun unscheduleAll()

}