package com.aboutme.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aboutme.app.AboutMeApp
import com.aboutme.app.rememberAboutMeAppState

const val AppRoute = "app"

fun NavController.navigateToApp() = navigate(AppRoute)

fun NavGraphBuilder.app(
    mainNavController: NavHostController
) {
    composable(
        route = AppRoute
    ) {
        AboutMeApp(
            appState = rememberAboutMeAppState(
                mainNavController = mainNavController
            )
        )
    }
}