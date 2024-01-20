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
     * @param hours The period in hours
     */
    suspend fun schedulePeriodically(hours: Long)

    /**
     * Unschedules all currently scheduled
     */
    suspend fun unscheduleAll()

}