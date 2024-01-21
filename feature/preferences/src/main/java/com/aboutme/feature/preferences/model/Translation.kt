package com.aboutme.feature.preferences.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.feature.preferences.R

internal val ColorTheme.localizedName: String
    @Composable get() = stringResource(
        id = when (this) {
            ColorTheme.Default -> R.string.color_theme_default
            ColorTheme.Dynamic -> R.string.color_theme_dynamic
        }
    )

internal val ColorMode.localizedName: String
    @Composable get() = stringResource(
        id = when (this) {
            ColorMode.Light -> R.string.color_mode_light
            ColorMode.Dark -> R.string.color_mode_dark
            ColorMode.System -> R.string.color_mode_system
        }
    )

internal val SyncOption.localizedName: String
    @Composable get() = stringResource(
        id = when (this) {
            SyncOption.OnEnter -> R.string.sync_option_onenter
            SyncOption.OnChange -> R.string.sync_option_onchange
            SyncOption.Periodically -> R.string.sync_option_periodically
            SyncOption.Not -> R.string.sync_option_not
        }
    )