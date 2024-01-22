package com.aboutme.feature.preferences.model

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal data class SyncPeriod(

    val value: Long,

    val unit: PeriodicSyncUnit

) {

    init {
        require(value > 0)
    }

}

internal fun Duration.toSyncPeriod() =
    if (inWholeMinutes < 60) SyncPeriod(inWholeMinutes, PeriodicSyncUnit.Minute)
    else if (inWholeHours < 24) SyncPeriod(inWholeHours, PeriodicSyncUnit.Hour)
    else SyncPeriod(inWholeDays, PeriodicSyncUnit.Day)

internal fun SyncPeriod.toDuration() = value.toDuration(
    when (unit) {
        PeriodicSyncUnit.Minute -> DurationUnit.MINUTES
        PeriodicSyncUnit.Hour -> DurationUnit.HOURS
        PeriodicSyncUnit.Day -> DurationUnit.DAYS
    }
)

