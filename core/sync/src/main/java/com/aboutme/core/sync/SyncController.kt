package com.aboutme.core.sync

/**
 * Exposes functionality related to the sync worker
 */
interface SyncController {

    /**
     * Schedules a sync right now
     */
    fun syncNow()

    /**
     * Schedules a synchronization periodically
     *
     * @param hours The period in hours
     */
    fun schedulePeriodically(hours: Long)

    /**
     * Unschedules all currently scheduled
     */
    fun unscheduleAll()

}