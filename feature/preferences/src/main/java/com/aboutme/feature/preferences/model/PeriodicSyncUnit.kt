package com.aboutme.feature.preferences.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aboutme.feature.preferences.R
import java.time.Period
import java.time.temporal.ChronoUnit

internal enum class PeriodicSyncUnit(
    val chronoUnit: ChronoUnit
) {

    Minute(ChronoUnit.MINUTES),

    Hour(ChronoUnit.HOURS),

    Day(ChronoUnit.DAYS)

}


internal val PeriodicSyncUnit.localizedName: String
    @Composable get() = stringResource(
        id = when (this) {
            PeriodicSyncUnit.Minute -> R.string.sync_periodic_unit_minute
            PeriodicSyncUnit.Hour -> R.string.sync_periodic_unit_hour
            PeriodicSyncUnit.Day -> R.string.sync_periodic_unit_days
        }
    )