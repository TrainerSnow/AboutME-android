package com.aboutme.feature.preferences.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aboutme.feature.preferences.screen.PreferencesScreen

private const val PreferencesRoute = "preferences"

fun NavController.navigateToPreferences(navOptions: NavOptions? = null) =
    navigate(PreferencesRoute, navOptions)

fun NavGraphBuilder.preferences(
    onReturn: () -> Unit
) {
    composable(
        route = PreferencesRoute
    ) {
        PreferencesScreen(
            onReturn = onReturn
        )
    }
}