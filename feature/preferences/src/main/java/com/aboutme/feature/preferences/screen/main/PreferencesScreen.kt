package com.aboutme.feature.preferences.screen.main

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
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.UserPreferences
import com.aboutme.core.ui.preferences.BasePreference
import com.aboutme.core.ui.preferences.PopupPreference
import com.aboutme.core.ui.preferences.PreferenceDivider
import com.aboutme.feature.preferences.R
import com.aboutme.feature.preferences.model.localizedName
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun PreferencesScreen(
    viewModel: PreferencesViewModel = hiltViewModel(),
    onReturn: () -> Unit,
    onGoToSyncPreferences: () -> Unit
) {
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                PreferencesUiEvent.Return -> onReturn()
                PreferencesUiEvent.GoToSyncPreferences -> onGoToSyncPreferences()
            }
        }
    }

    preferences?.let {
        PreferencesScreen(
            preferences = it,
            onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreferencesScreen(
    preferences: UserPreferences,
    onEvent: (PreferencesEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.preferences_screen_title)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onEvent(PreferencesEvent.Return) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.preferences_screen_navigate_back)
                    )
                }
            }
        )
        VisualCategory(
            modifier = Modifier.fillMaxWidth(),
            colorTheme = preferences.colorTheme,
            colorMode = preferences.colorMode,
            onEvent = onEvent
        )
        SyncCategory(
            modifier = Modifier.fillMaxWidth(),
            onEvent = onEvent
        )
    }
}

@Composable
private fun VisualCategory(
    modifier: Modifier = Modifier,
    colorTheme: ColorTheme,
    colorMode: ColorMode,
    onEvent: (PreferencesEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        PreferenceDivider {
            Text(
                text = stringResource(R.string.category_visuals)
            )
        }
        PopupPreference(
            items = ColorTheme.entries,
            selectedItem = colorTheme,
            nameOf = { localizedName },
            onSelect = {
                onEvent(PreferencesEvent.ChangeColorTheme(it))
            },
            title = {
                Text(
                    text = stringResource(R.string.preference_color_theme)
                )
            }
        )
        PopupPreference(
            items = ColorMode.entries,
            selectedItem = colorMode,
            nameOf = { localizedName },
            onSelect = {
                onEvent(PreferencesEvent.ChangeColorMode(it))
            },
            title = {
                Text(
                    text = stringResource(R.string.preference_color_mode)
                )
            }
        )
    }
}

@Composable
private fun SyncCategory(
    modifier: Modifier = Modifier,
    onEvent: (PreferencesEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        PreferenceDivider {
            Text(
                text = stringResource(R.string.category_sync)
            )
        }
        BasePreference(
            title = {
                Text(
                    text = stringResource(R.string.preference_open_sync)
                )
            },
            onClick = {
                onEvent(PreferencesEvent.OpenSyncPreferences)
            }
        )
    }
}