package com.aboutme.feature.preferences.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aboutme.feature.preferences.screen.main.PreferencesScreen

internal const val MainPreferencesRoute = "main_preferences"

internal fun NavController.navigateToMainPreferences(navOptions: NavOptions? = null) =
    navigate(MainPreferencesRoute, navOptions)

internal fun NavGraphBuilder.mainPreferences(
    onReturn: () -> Unit,
    onGoToSyncPreferences: () -> Unit
) {
    composable(
        route = MainPreferencesRoute
    ) {
        PreferencesScreen(
            onReturn = onReturn,
            onGoToSyncPreferences = onGoToSyncPreferences
        )
    }
}