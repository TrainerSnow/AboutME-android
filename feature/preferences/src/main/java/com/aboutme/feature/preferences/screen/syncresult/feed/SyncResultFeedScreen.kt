package com.aboutme.feature.preferences.screen.syncresult.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.core.ui.feed.syncresult.SyncResultFeedState
import com.aboutme.core.ui.feed.syncresult.syncResultFeed
import com.aboutme.feature.preferences.R
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SyncResultFeedScreen(
    viewModel: SyncResultFeedViewModel = hiltViewModel(),

    onReturn: () -> Unit,
    onGoToDetail: (SyncResult.Success) -> Unit
) {
    val feedState by viewModel.feedState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                is SyncResultFeedUiEvent.GoToDetail -> onGoToDetail(it.syncResult)
                SyncResultFeedUiEvent.Return -> onReturn()
            }
        }
    }

    SyncResultFeedScreen(
        state = feedState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SyncResultFeedScreen(
    state: SyncResultFeedState,
    onEvent: (SyncResultFeedEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.sync_result_list_title)
                )
            },
            navigationIcon = {
                IconButton(onClick = { onEvent(SyncResultFeedEvent.Return) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.sync_result_list_return)
                    )
                }
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            syncResultFeed(
                state = state,
                onClick = {
                    onEvent(SyncResultFeedEvent.GoToDetail(it))
                }
            )
        }
    }
}