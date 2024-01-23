package com.aboutme.feature.preferences.screen.syncresult.feed;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.repository.SyncResultRepository
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.ui.feed.syncresult.SyncResultFeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class SyncResultFeedViewModel @Inject constructor(
    val syncRepo: SyncResultRepository
) : AboutMeViewModel<SyncResultFeedEvent, SyncResultFeedUiEvent, Unit>() {

    override val initialState = Unit

    val feedState = syncRepo
        .getAll()
        .map {
            SyncResultFeedState.Success(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SyncResultFeedState.Loading
        )

    override fun handleEvent(event: SyncResultFeedEvent) = when (event) {
        is SyncResultFeedEvent.GoToDetail -> {
            triggerUiEvent(SyncResultFeedUiEvent.GoToDetail(event.syncResult))
        }

        SyncResultFeedEvent.Return -> {
            triggerUiEvent(SyncResultFeedUiEvent.Return)
        }
    }
}