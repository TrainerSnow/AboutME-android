package com.aboutme.feature.preferences.screen.sync

import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.feature.preferences.model.SyncPeriod

internal sealed interface SyncPreferencesEvent {

    data class ChangeSyncOption(val syncOption: SyncOption) : SyncPreferencesEvent

    data class ChangeSyncPeriod(val syncPeriod: SyncPeriod) : SyncPreferencesEvent

    data object GoToAuth : SyncPreferencesEvent

    data object SyncNow : SyncPreferencesEvent

    data class SyncInfo(val syncResult: SyncResult.Success) : SyncPreferencesEvent

    data object ToggleSyncEnabled : SyncPreferencesEvent

    data object Return : SyncPreferencesEvent

    data object GoToSyncResultFeed : SyncPreferencesEvent

}

internal sealed interface SyncPreferencesUiEvent {

    data object Return : SyncPreferencesUiEvent

    data object GoToSyncResultFeed : SyncPreferencesUiEvent

    data object GoToAuth : SyncPreferencesUiEvent

    data class GoToSyncResultInfo(val syncResult: SyncResult.Success): SyncPreferencesUiEvent

}
