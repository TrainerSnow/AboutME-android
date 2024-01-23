package com.aboutme.feature.preferences.screen.sync

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.core.model.preferences.SyncPreferences
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.core.model.sync.SyncTrafficInfo
import com.aboutme.core.ui.preferences.BasePreference
import com.aboutme.core.ui.preferences.PopupPreference
import com.aboutme.core.ui.preferences.PreferenceDivider
import com.aboutme.core.ui.preferences.SwitchPreference
import com.aboutme.feature.preferences.R
import com.aboutme.feature.preferences.components.RecentSyncBanner
import com.aboutme.feature.preferences.components.SyncPeriodPreference
import com.aboutme.feature.preferences.model.localizedName
import com.aboutme.feature.preferences.model.toSyncPeriod
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun SyncPreferencesScreen(
    viewModel: SyncPreferencesViewModel = hiltViewModel(),
    onReturn: () -> Unit,
    onGoToSyncResultFeed: () -> Unit,
    onGoToAuth: () -> Unit,
    onGoToSyncDetail: (SyncResult.Success) -> Unit
) {
    val syncPrefs by viewModel.syncPrefs.collectAsStateWithLifecycle()
    val syncState by viewModel.syncState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                SyncPreferencesUiEvent.Return -> onReturn()
                SyncPreferencesUiEvent.GoToSyncResultFeed -> onGoToSyncResultFeed()
                SyncPreferencesUiEvent.GoToAuth -> onGoToAuth()
                is SyncPreferencesUiEvent.GoToSyncResultInfo -> onGoToSyncDetail(it.syncResult)
            }
        }
    }

    if (syncPrefs != null) {
        SyncPreferencesScreen(
            syncPrefs = syncPrefs!!,
            syncState = syncState,
            onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SyncPreferencesScreen(
    syncPrefs: SyncPreferences,
    syncState: SyncState,
    onEvent: (SyncPreferencesEvent) -> Unit
) {
    val isSyncEnabled = syncPrefs.syncOption != SyncOption.Not
    val isPeriodicallyEnabled = syncPrefs.syncOption == SyncOption.Periodically
    val isSyncing = syncState is SyncState.Syncing

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(
                        if (!isSyncing) R.string.sync_preferences_screen_title
                        else R.string.sync_preferences_screen_title_syncing
                    )
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onEvent(SyncPreferencesEvent.Return) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.sync_preferences_return)
                    )
                }
            }
        )
        if (isSyncing) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        if (syncState is SyncState.NotSyncing) {
            RecentSyncBanner(
                modifier = Modifier
                    .padding(16.dp),
                syncInfo = syncState.mostRecent,
                onSyncNow = { onEvent(SyncPreferencesEvent.SyncNow) },
                onAuth = { onEvent(SyncPreferencesEvent.GoToAuth) },
                onMoreInfo = { onEvent(SyncPreferencesEvent.SyncInfo(it)) }
            )
        }

        PreferenceDivider(
            enabled = !isSyncing
        ) {
            Text(
                text = stringResource(R.string.sync_category_auto)
            )
        }
        SwitchPreference(
            switched = isSyncEnabled,
            onSwitchedChange = {
                onEvent(SyncPreferencesEvent.ToggleSyncEnabled)
            },
            title = {
                Text(
                    text = stringResource(R.string.preference_sync_enabled)
                )
            },
            enabled = !isSyncing
        )

        PopupPreference(
            items = SyncOption.entries.filterNot { it == SyncOption.Not },
            selectedItem = if (syncPrefs.syncOption == SyncOption.Not) SyncOption.OnEnter else syncPrefs.syncOption,
            nameOf = { localizedName },
            onSelect = {
                onEvent(SyncPreferencesEvent.ChangeSyncOption(it))
            },
            enabled = isSyncEnabled && !isSyncing
        ) {
            Text(
                text = stringResource(R.string.preference_sync_option)
            )
        }

        SyncPeriodPreference(
            modifier = Modifier
                .fillMaxWidth(),
            period = syncPrefs.period.toSyncPeriod(),
            onPeriodChange = {
                onEvent(SyncPreferencesEvent.ChangeSyncPeriod(it))
            },
            enabled = !isSyncing && isPeriodicallyEnabled
        )

        PreferenceDivider {
            Text(
                text = stringResource(R.string.sync_category_logs)
            )
        }
        BasePreference(
            modifier = Modifier
                .fillMaxWidth(),
            title = {
                Text(
                    text = stringResource(R.string.sync_preferences_log_title)
                )
            },
            subtitle = {
                Text(
                    text = stringResource(R.string.sync_preferences_log_summary)
                )
            },
            onClick = {
                onEvent(SyncPreferencesEvent.GoToSyncResultFeed)
            }
        )
    }
}

@Preview
@Composable
private fun SyncPreferencesScreenPreviewNoAuth() {
    Surface {
        SyncPreferencesScreen(
            syncPrefs = SyncPreferences(
                onlyWifi = true,
                period = 5L.toDuration(DurationUnit.HOURS),
                syncOption = SyncOption.Periodically
            ),
            syncState = SyncState.NotSyncing(
                SyncResult.NotAuthorized(
                    Instant.now().minusSeconds(240),
                    Instant.now()
                )
            ),
            onEvent = { }
        )
    }
}

@Preview
@Composable
private fun SyncPreferencesScreenPreviewSyncing() {
    Surface {
        SyncPreferencesScreen(
            syncPrefs = SyncPreferences(
                onlyWifi = true,
                period = 5L.toDuration(DurationUnit.HOURS),
                syncOption = SyncOption.Periodically
            ),
            syncState = SyncState.Syncing,
            onEvent = { }
        )
    }
}

@Preview
@Composable
private fun SyncPreferencesScreenPreviewSuccess() {
    Surface {
        SyncPreferencesScreen(
            syncPrefs = SyncPreferences(
                onlyWifi = true,
                period = 5L.toDuration(DurationUnit.HOURS),
                syncOption = SyncOption.Periodically
            ),
            syncState = SyncState.NotSyncing(
                SyncResult.Success(
                    started = Instant.now().minusSeconds(240),
                    ended = Instant.now(),
                    SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                    SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                    SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                    SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                    SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                    SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                    SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                    SyncTrafficInfo(0, 0, 0, 0, 0, 0)
                )
            ),
            onEvent = { }
        )
    }
}