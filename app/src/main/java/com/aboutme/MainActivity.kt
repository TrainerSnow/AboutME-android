package com.aboutme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.aboutme.core.ui.locals.ProvideWindowSizeClass
import com.aboutme.core.ui.theme.AboutMeTheme
import com.aboutme.feature.auth.navigation.AuthRoute
import com.aboutme.feature.auth.navigation.authentication
import com.aboutme.feature.auth.navigation.navigateToAuth
import com.aboutme.feature.preferences.navigation.preferences
import com.aboutme.navigation.AppRoute
import com.aboutme.navigation.app
import com.aboutme.navigation.navigateToApp
import com.aboutme.profile.navigation.profile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var splashScreen: SplashScreen

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var state: MainActivityState by mutableStateOf(MainActivityState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state = it }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            state == MainActivityState.Loading
        }

        setContent {
            ProvideWindowSizeClass(calculateWindowSizeClass(this)) {
                AboutMeTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()

                        if (state !is MainActivityState.Loading) {
                            NavHost(
                                navController = navController,
                                startDestination = if (state is MainActivityState.NonAuthenticated) AuthRoute else AppRoute
                            ) {
                                app(
                                    mainNavController = navController
                                )
                                authentication(
                                    onAuthenticate = {
                                        navController.navigateToApp()
                                    }
                                )
                                profile(
                                    onGoToAuth = navController::navigateToAuth,
                                    onGoToChangeEmail = { TODO() },
                                    onGoToResetPassword = { TODO() },
                                    onReturn = navController::popBackStack
                                )
                                preferences(
                                    onReturn = navController::popBackStack
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}