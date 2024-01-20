package com.aboutme.core.datastore.mapping

import com.aboutme.core.datastore.proto.ColorModeProto
import com.aboutme.core.datastore.proto.ColorThemeProto
import com.aboutme.core.datastore.proto.SyncPreferencesProto
import com.aboutme.core.datastore.proto.UserPreferencesProto
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncPreferences
import com.aboutme.core.model.preferences.UserPreferences
import kotlin.time.Duration

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
    if (not) SyncPreferences.Not
    else if (onChange) SyncPreferences.EnabledSyncPreferences.OnChange(onlyWifi)
    else if (onEnter) SyncPreferences.EnabledSyncPreferences.OnEnter(onEnter)
    else if (duration != "") SyncPreferences.EnabledSyncPreferences.Periodically(
        onlyWifi = onlyWifi,
        period = Duration.parseOrNull(duration)
            ?: throw IllegalStateException("Could not parse a 'SyncPreferencesProto' to the model version!")
    )
    else throw IllegalStateException("Could not parse a 'SyncPreferencesProto' to the model version!")