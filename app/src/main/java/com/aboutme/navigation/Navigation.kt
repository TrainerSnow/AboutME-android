package com.aboutme.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aboutme.app.AboutMeApp
import com.aboutme.app.rememberAboutMeAppState

const val AppRoute = "app"

fun NavController.navigateToApp(navOptions: NavOptions? = null) = navigate(AppRoute, navOptions)

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