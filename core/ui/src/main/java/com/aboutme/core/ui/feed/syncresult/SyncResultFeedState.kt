package com.aboutme.core.ui.feed.syncresult;

import com.aboutme.core.model.sync.SyncResult

sealed interface SyncResultFeedState {

    data object Loading: SyncResultFeedState

    data class Success(val syncResults: List<SyncResult>): SyncResultFeedState

}