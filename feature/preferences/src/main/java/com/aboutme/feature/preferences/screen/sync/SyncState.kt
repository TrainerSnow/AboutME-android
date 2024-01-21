package com.aboutme.feature.preferences.screen.sync

import com.aboutme.core.model.sync.SyncResult

internal sealed interface SyncState {

    data object Syncing : SyncState

    data object None: SyncState

    data class NotSyncing(val mostRecent: SyncResult) : SyncState

}
