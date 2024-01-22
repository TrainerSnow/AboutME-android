package com.aboutme.feature.preferences.screen.root

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.aboutme.feature.preferences.navigation.MainPreferencesRoute
import com.aboutme.feature.preferences.navigation.mainPreferences
import com.aboutme.feature.preferences.navigation.navigateToSyncPreferences
import com.aboutme.feature.preferences.navigation.syncPreferences

@Composable
internal fun RootPreferencesScreen(
    onReturn: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainPreferencesRoute
    ) {
        mainPreferences(onReturn, navController::navigateToSyncPreferences)
        syncPreferences(navController::popBackStack)
    }
}