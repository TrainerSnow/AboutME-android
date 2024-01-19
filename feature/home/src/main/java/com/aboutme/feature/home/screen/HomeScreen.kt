package com.aboutme.feature.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aboutme.core.ui.adaptive.AdaptiveTopAppBar
import com.aboutme.core.ui.feed.dailydata.DailyDataFeedState
import com.aboutme.core.ui.feed.dailydata.dailyDataFeed
import com.aboutme.feature.home.R
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onAuthError: () -> Unit,
    onLogOut: () -> Unit,
    onGoToProfile: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dailyFeedState by viewModel.dailyFeedState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                HomeUiEvent.AuthError -> onAuthError()
                HomeUiEvent.LogOut -> onLogOut()
                HomeUiEvent.GoToProfile -> onGoToProfile()
            }
        }
    }

    HomeScreen(
        feedState = dailyFeedState,
        uiState = state.uiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    feedState: DailyDataFeedState,
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AdaptiveTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    text = stringResource(R.string.home_title)
                )
            },
            actions = {
                IconButton(
                    onClick = { onEvent(HomeEvent.ToggleUserPopup) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(R.string.toggle_user_popup)
                    )
                }

                DropdownMenu(
                    expanded = uiState.showUserPopup,
                    onDismissRequest = { onEvent(HomeEvent.ToggleUserPopup) }
                ) {
                    UserPopupContent(
                        onLogout = { onEvent(HomeEvent.LogOut) },
                        onLogoutAll = { onEvent(HomeEvent.LogOutAll) },
                    ) { onEvent(HomeEvent.GoToProfile) }
                }
            }
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 450.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            dailyDataFeed(feedState) { }
        }
    }
}

@Composable
private fun UserPopupContent(
    onLogout: () -> Unit,
    onLogoutAll: () -> Unit,
    onProfile: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(R.string.user_popup_profile)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = stringResource(R.string.user_popup_logout)
            )
        },
        onClick = onProfile
    )
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(R.string.user_popup_logout)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Logout,
                contentDescription = stringResource(R.string.user_popup_logout)
            )
        },
        onClick = onLogout
    )
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(R.string.user_popup_logout_all),
                color = MaterialTheme.colorScheme.error
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Logout,
                contentDescription = stringResource(R.string.user_popup_logout_all),
                tint = MaterialTheme.colorScheme.error
            )
        },
        onClick = onLogoutAll
    )
}