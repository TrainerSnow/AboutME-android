package com.aboutme.feature.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aboutme.core.model.data.UserData
import com.aboutme.feature.auth.screen.login.LogInScreen
import com.aboutme.feature.auth.screen.signup.SignUpScreen

private const val RouteSignUp = "signup"
private const val RouteLogIn = "login"

@Composable
internal fun AuthNavigationRoot(
    doSignupFirst: Boolean = true,
    onAuthenticate: (UserData) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (doSignupFirst) RouteSignUp else RouteLogIn
    ) {
        signUpRoute(
            onContinue = onAuthenticate,
            onGoToLogIn = {
                navController.navigate(RouteLogIn)
            }
        )

        logInRoute(
            onContinue = onAuthenticate,
            onGoToSignUp = {
                navController.navigate(RouteSignUp)
            }
        )
    }
}

private fun NavGraphBuilder.signUpRoute(
    onContinue: (UserData) -> Unit,
    onGoToLogIn: () -> Unit
) {
    composable(
        route = RouteSignUp
    ) {
        SignUpScreen(
            onContinue = onContinue,
            onGoToLogIn = onGoToLogIn
        )
    }
}

private fun NavGraphBuilder.logInRoute(
    onContinue: (UserData) -> Unit,
    onGoToSignUp: () -> Unit
) {
    composable(
        route = RouteLogIn
    ) {
        LogInScreen(
            onContinue = onContinue,
            onGoToSignUp = onGoToSignUp
        )
    }
}