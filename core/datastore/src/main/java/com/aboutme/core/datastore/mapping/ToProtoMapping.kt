package com.aboutme.core.datastore.mapping

import com.aboutme.core.datastore.proto.ColorModeProto
import com.aboutme.core.datastore.proto.ColorThemeProto
import com.aboutme.core.datastore.proto.SyncPreferencesProto
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncPreferences

fun ColorTheme.toProto() = when (this) {
    ColorTheme.Default -> ColorThemeProto.Default
    ColorTheme.Dynamic -> ColorThemeProto.Dynamic
}

fun ColorMode.toProto() = when (this) {
    ColorMode.Light -> ColorModeProto.Light
    ColorMode.Dark -> ColorModeProto.Dark
    ColorMode.System -> ColorModeProto.System
}

fun SyncPreferences.toProto() = SyncPreferencesProto
    .newBuilder()
    .setNot(this is SyncPreferences.Not)
    .setDuration(
        (this as? SyncPreferences.EnabledSyncPreferences.Periodically)?.period?.toString() ?: ""
    )
    .setOnChange(this is SyncPreferences.EnabledSyncPreferences.OnChange)
    .setOnEnter(this is SyncPreferences.EnabledSyncPreferences.OnEnter)
    .setOnlyWifi((this as? SyncPreferences.EnabledSyncPreferences)?.onlyWifi ?: false)