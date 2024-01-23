package com.aboutme.feature.preferences.screen.syncresult.feed;

import com.aboutme.core.model.sync.SyncResult

internal sealed interface SyncResultFeedEvent {

    data object Return : SyncResultFeedEvent

    data class GoToDetail(val syncResult: SyncResult.Success) : SyncResultFeedEvent

}

internal sealed interface SyncResultFeedUiEvent {

    data object Return : SyncResultFeedUiEvent

    data class GoToDetail(val syncResult: SyncResult.Success) : SyncResultFeedUiEvent

}