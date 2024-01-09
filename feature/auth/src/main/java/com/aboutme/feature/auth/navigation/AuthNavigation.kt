package com.aboutme.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aboutme.core.model.data.UserData

private const val ArgDoSignupFirst = "do_signup"

const val AuthRoute = "authentication/{$ArgDoSignupFirst}"

private fun authRouteWithArgs(doSignUp: Boolean) =
    AuthRoute.replace("{$ArgDoSignupFirst}", doSignUp.toString())

fun NavGraphBuilder.authentication(
    onAuthenticate: (UserData) -> Unit
) {
    composable(
        route = AuthRoute,
        arguments = listOf(
            navArgument(
                name = ArgDoSignupFirst,
                builder = {
                    defaultValue = true
                    type = NavType.BoolType
                }
            )
        )
    ) {
        AuthNavigationRoot (
            doSignupFirst = it.arguments?.getBoolean(ArgDoSignupFirst) ?: true,
            onAuthenticate = onAuthenticate
        )
    }
}

fun NavController.navigateToAuth(
    doSignupFirst: Boolean = true
) = navigate(authRouteWithArgs(doSignupFirst))