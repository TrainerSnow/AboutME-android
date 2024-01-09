package com.aboutme.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aboutme.profile.screen.ProfileScreen

const val ProfileRoute = "profile"

fun NavController.navigateToProfile() = navigate(ProfileRoute)

fun NavGraphBuilder.profile(
    onGoToAuth: () -> Unit,
    onGoToChangeEmail: () -> Unit,
    onGoToResetPassword: () -> Unit,
    onReturn: () -> Unit
) {
    composable(
        route = ProfileRoute
    ) {
        ProfileScreen(
            onGoToAuth = onGoToAuth,
            onGoToChangeEmail = onGoToChangeEmail,
            onGoToResetPassword = onGoToResetPassword,
            onReturn = onReturn
        )
    }
}