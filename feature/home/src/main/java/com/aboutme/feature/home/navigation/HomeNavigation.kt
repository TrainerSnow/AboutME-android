package com.aboutme.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aboutme.feature.home.screen.HomeScreen

const val HomeRoute = "home"

fun NavController.navigateToHome() = navigate(HomeRoute)

fun NavGraphBuilder.home(
    onAuthError: () -> Unit,
    onLogOut: () -> Unit,
    onGoToProfile: () -> Unit,
    onGoToPreferences: () -> Unit
) {
    composable(
        route = HomeRoute
    ) {
        HomeScreen(
            onAuthError = onAuthError,
            onLogOut = onLogOut,
            onGoToProfile = onGoToProfile,
            onGoToPreferences = onGoToPreferences
        )
    }
}