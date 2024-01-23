package com.aboutme.feature.preferences.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.feature.preferences.screen.sync.SyncPreferencesScreen

internal const val SyncPreferencesRoute = "sync_preferences"

internal fun NavController.navigateToSyncPreferences(navOptions: NavOptions? = null) =
    navigate(SyncPreferencesRoute, navOptions)

internal fun NavGraphBuilder.syncPreferences(
    onReturn: () -> Unit,
    onGoToSyncResultFeed: () -> Unit,
    onGoToAuth: () -> Unit,
    onGoToSyncDetail: (SyncResult.Success) -> Unit
) {
    composable(
        route = SyncPreferencesRoute
    ) {
        SyncPreferencesScreen(
            onReturn = onReturn,
            onGoToSyncResultFeed = onGoToSyncResultFeed,
            onGoToAuth = onGoToAuth,
            onGoToSyncDetail = onGoToSyncDetail
        )
    }
}