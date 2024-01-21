package com.aboutme.feature.preferences.screen

import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.feature.preferences.model.SyncPeriod

internal sealed interface PreferencesEvent {

    data object Return: PreferencesEvent

    data object ToggleSyncEnabled: PreferencesEvent

    data class ChangeSyncOption(val syncOption: SyncOption): PreferencesEvent

    data class ChangeSyncPeriod(val syncPeriod: SyncPeriod): PreferencesEvent

    data class ChangeColorTheme(val colorTheme: ColorTheme): PreferencesEvent

    data class ChangeColorMode(val colorMode: ColorMode): PreferencesEvent
}

internal sealed interface PreferencesUiEvent {

    data object Return: PreferencesUiEvent

}