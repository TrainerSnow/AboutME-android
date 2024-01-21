package com.aboutme.feature.preferences.screen.sync

import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.feature.preferences.model.SyncPeriod

internal sealed interface SyncPreferencesEvent {

    data class ChangeSyncOption(val syncOption: SyncOption) : SyncPreferencesEvent

    data class ChangeSyncPeriod(val syncPeriod: SyncPeriod) : SyncPreferencesEvent

    data object ToggleSyncEnabled: SyncPreferencesEvent

    data object Return : SyncPreferencesEvent

}

internal sealed interface SyncPreferencesUiEvent {

    data object Return : SyncPreferencesUiEvent

}
