package com.aboutme.feature.preferences.screen.main

import com.aboutme.core.data.repository.PreferencesRepository
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.feature.preferences.model.SyncPeriod

internal sealed interface PreferencesEvent {

    data object Return: PreferencesEvent

    data object OpenSyncPreferences: PreferencesEvent

    data class ChangeColorTheme(val colorTheme: ColorTheme): PreferencesEvent

    data class ChangeColorMode(val colorMode: ColorMode): PreferencesEvent
}

internal sealed interface PreferencesUiEvent {

    data object Return: PreferencesUiEvent

    data object GoToSyncPreferences: PreferencesUiEvent

}