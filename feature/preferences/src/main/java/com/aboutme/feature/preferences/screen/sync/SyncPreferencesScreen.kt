package com.aboutme.feature.preferences.screen.sync

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.core.model.preferences.SyncPreferences
import com.aboutme.core.ui.preferences.PopupPreference
import com.aboutme.core.ui.preferences.PreferenceDivider
import com.aboutme.core.ui.preferences.SwitchPreference
import com.aboutme.feature.preferences.R
import com.aboutme.feature.preferences.components.SyncPeriodPreference
import com.aboutme.feature.preferences.model.localizedName
import com.aboutme.feature.preferences.model.toSyncPeriod
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SyncPreferencesScreen(
    viewModel: SyncPreferencesViewModel = hiltViewModel(),
    onReturn: () -> Unit
) {
    val syncPrefs by viewModel.syncPrefs.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                SyncPreferencesUiEvent.Return -> onReturn()
            }
        }
    }

    syncPrefs?.let {
        SyncPreferencesScreen(
            syncPrefs = it,
            onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SyncPreferencesScreen(
    syncPrefs: SyncPreferences,
    onEvent: (SyncPreferencesEvent) -> Unit
) {
    val isSyncEnabled = syncPrefs.syncOption != SyncOption.Not
    val isPeriodicallyEnabled = syncPrefs.syncOption == SyncOption.Periodically

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.sync_preferences_screen_title)
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
        PreferenceDivider {
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
            }
        )

        PopupPreference(
            items = SyncOption.entries.filterNot { it == SyncOption.Not },
            selectedItem = if (syncPrefs.syncOption == SyncOption.Not) SyncOption.OnEnter else syncPrefs.syncOption,
            nameOf = { localizedName },
            onSelect = {
                onEvent(SyncPreferencesEvent.ChangeSyncOption(it))
            },
            enabled = isSyncEnabled
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
            enabled = isPeriodicallyEnabled
        )
    }
}