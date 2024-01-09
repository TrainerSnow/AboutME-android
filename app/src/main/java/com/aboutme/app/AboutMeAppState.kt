package com.aboutme.app;

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aboutme.feature.auth.navigation.navigateToAuth
import com.aboutme.feature.home.navigation.HomeRoute
import com.aboutme.navigation.TopLevelDestinations
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun rememberAboutMeAppState(
    mainNavController: NavHostController,
    appNavController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope()
) = remember(
    mainNavController, appNavController, scope
) {
    AboutMeAppState(mainNavController, appNavController, scope)
}

internal class AboutMeAppState(

    /**
     * The top level nav controller. This does not control the nav bar/rail, but destinations like auth, app and settings.
     */
    val mainNavController: NavHostController,

    /**
     * The nav controller which controls the destinations shown on the nav bar/rail, and possibly more.
     */
    val appNavController: NavHostController,

    /**
     * The scope in which miscellaneous jobs are launched into
     */
    val scope: CoroutineScope

) {

    /**
     * Holds the route that the app nav controller currently is on
     */
    private val currentDestination: NavDestination?
        @Composable get() = appNavController
            .currentBackStackEntryAsState().value?.destination

    /**
     * Calculates the current [TopLevelDestinations]. Null, if none of the ones from the nav bar/rail is selected
     */
    val currentTopLevelDestination: TopLevelDestinations?
        @Composable get() = when (currentDestination?.route) {
            HomeRoute -> TopLevelDestinations.Home
            else -> null
        }

    /**
     * Navigates back to the authentication screen
     */
    fun returnToAuth() = mainNavController
        .navigateToAuth()

    /**
     * Navigates to the [TopLevelDestinations]
     */
    fun navigateTo(destination: TopLevelDestinations) = appNavController.navigate(destination.route)

}