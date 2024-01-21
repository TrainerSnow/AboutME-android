package com.aboutme.core.datastore.mapping

import com.aboutme.core.datastore.proto.ColorModeProto
import com.aboutme.core.datastore.proto.ColorThemeProto
import com.aboutme.core.datastore.proto.SyncPreferencesProto
import com.aboutme.core.datastore.proto.UserPreferencesProto
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.core.model.preferences.SyncPreferences
import com.aboutme.core.model.preferences.UserPreferences
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal fun UserPreferencesProto.toModel() = UserPreferences(
    colorMode = colorMode.toModel(),
    colorTheme = colorTheme.toModel(),
    syncPreferences = syncPreferences.toModel()
)

internal fun ColorModeProto.toModel() = when (this) {
    ColorModeProto.Light -> ColorMode.Light
    ColorModeProto.Dark -> ColorMode.Dark
    ColorModeProto.System -> ColorMode.System
    ColorModeProto.UNRECOGNIZED -> ColorMode.System
}

internal fun ColorThemeProto.toModel() = when (this) {
    ColorThemeProto.Default -> ColorTheme.Default
    ColorThemeProto.Dynamic -> ColorTheme.Dynamic
    ColorThemeProto.UNRECOGNIZED -> ColorTheme.Default
}

internal fun SyncPreferencesProto.toModel() =
    SyncPreferences(
        onlyWifi = onlyWifi,
        period = duration.parseDuration(),
        syncOption = if (not) SyncOption.Not
        else if (onEnter) SyncOption.OnEnter
        else if (onChange) SyncOption.OnChange
        else SyncOption.Periodically
    )

private fun String.parseDuration() = Duration.parseOrNull(this)
    ?: (5L).toDuration(DurationUnit.HOURS)