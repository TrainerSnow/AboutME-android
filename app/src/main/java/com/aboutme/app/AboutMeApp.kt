package com.aboutme.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigation.suite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import com.aboutme.feature.home.navigation.HomeRoute
import com.aboutme.feature.home.navigation.home
import com.aboutme.feature.preferences.navigation.navigateToPreferences
import com.aboutme.navigation.TopLevelDestinations
import com.aboutme.profile.navigation.navigateToProfile

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
@Composable
internal fun AboutMeApp(
    appState: AboutMeAppState
) {
    val selected = appState.currentTopLevelDestination

    Scaffold {
        NavigationSuiteScaffold(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding()
                ),
            navigationSuiteItems = {
                aboutMeNavItems(selected, appState::navigateTo)
            }
        ) {
            NavHost(
                navController = appState.appNavController,
                startDestination = HomeRoute
            ) {
                appNavGraph(
                    navigateToAuth = appState::returnToAuth,
                    goToProfile = appState.mainNavController::navigateToProfile,
                    onGoToPreferences = appState.mainNavController::navigateToPreferences
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
private fun NavigationSuiteScope.aboutMeNavItems(
    selected: TopLevelDestinations?,
    onNavigate: (TopLevelDestinations) -> Unit
) {
    TopLevelDestinations.entries.forEach { item ->
        item(
            selected = selected == item,
            onClick = {
                onNavigate(item)
            },
            icon = {
                Icon(
                    imageVector = item.icon,
                    contentDescription = stringResource(item.label)
                )
            },
            label = {
                Text(
                    text = stringResource(item.label)
                )
            }
        )
    }
}

private fun NavGraphBuilder.appNavGraph(
    navigateToAuth: () -> Unit,
    goToProfile: () -> Unit,
    onGoToPreferences: () -> Unit
) {
    home(
        onAuthError = navigateToAuth,
        onLogOut = navigateToAuth,
        onGoToProfile = goToProfile,
        onGoToPreferences = onGoToPreferences
    )
}