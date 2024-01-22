package com.aboutme.core.datastore.mapping

import com.aboutme.core.datastore.proto.ColorModeProto
import com.aboutme.core.datastore.proto.ColorThemeProto
import com.aboutme.core.datastore.proto.SyncPreferencesProto
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncOption
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

fun SyncPreferences.toProto(): SyncPreferencesProto = SyncPreferencesProto
    .newBuilder()
    .setNot(syncOption == SyncOption.Not)
    .setDuration(period.toString())
    .setOnChange(syncOption == SyncOption.OnChange)
    .setOnEnter(syncOption == SyncOption.OnEnter)
    .setOnlyWifi(onlyWifi)
    .setPeriodically(syncOption == SyncOption.Periodically)
    .build()