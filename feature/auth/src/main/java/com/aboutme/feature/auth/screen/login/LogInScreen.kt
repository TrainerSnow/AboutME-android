package com.aboutme.feature.auth.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aboutme.core.model.data.UserData
import com.aboutme.core.ui.locals.LocalWindowSizeClass
import com.aboutme.feature.auth.R
import com.aboutme.feature.auth.components.AuthBottomSection
import com.aboutme.feature.auth.components.AuthErrorSection
import com.aboutme.feature.auth.components.AuthScreen
import com.snow.core.input.component.OutlinedTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun LogInScreen(
    viewModel: LogInViewModel = hiltViewModel(),

    onContinue: (UserData) -> Unit,
    onGoToSignUp: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uiEvents = viewModel.uiEvents

    LaunchedEffect(true) {
        uiEvents.collectLatest {
            when (it) {
                is LogInUiEvent.Continue -> {
                    onContinue(it.authUser.user)
                }

                LogInUiEvent.GoToSignUp -> {
                    onGoToSignUp()
                }
            }
        }
    }

    LogInScreen(
        uiState = state.uiState,
        authState = state.authState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun LogInScreen(
    uiState: LogInUiState,
    authState: LogInAuthState,
    onEvent: (LogInEvent) -> Unit
) {
    AuthScreen(
        modifier = Modifier.fillMaxSize()
    ) {
        val tablet = LocalWindowSizeClass.current.run {
            heightSizeClass > WindowHeightSizeClass.Compact && widthSizeClass > WindowWidthSizeClass.Compact
        }

        val modifier = if (tablet) Modifier
        else Modifier.fillMaxWidth()

        HeaderSection(
            modifier = modifier
                .weight(2F)
        )
        CredentialsSection(
            modifier = modifier
                .weight(3F),
            uiState = uiState,
            onEvent = onEvent
        )
        AuthErrorSection(
            modifier = modifier
                .weight(1F),
            authState = authState.toAuthState()
        )
        AuthBottomSection(
            modifier = modifier
                .weight(1F),
            text = stringResource(R.string.signup_instead),
            buttonText = stringResource(R.string.login_title),
            onTextClick = { onEvent(LogInEvent.GoToSignUp) },
            onButtonClick = { onEvent(LogInEvent.LogIn) },
            tablet = tablet
        )
    }
}


@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun CredentialsSection(
    modifier: Modifier = Modifier,
    uiState: LogInUiState,
    onEvent: (LogInEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement
            .spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onEvent(LogInEvent.ChangeEmail(it)) },
            label = {
                Text(text = stringResource(R.string.email))
            }
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = {
                onEvent(LogInEvent.ChangePassword(it))
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onEvent(LogInEvent.ToggleShowPassword)
                    }
                ) {
                    Icon(
                        imageVector = if (uiState.showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = stringResource(
                            id = if (uiState.showPassword) R.string.hide_password else R.string.show_password
                        )
                    )
                }
            },
            visualTransformation = if (uiState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            label = {
                Text(text = stringResource(R.string.password))
            }
        )
    }
}