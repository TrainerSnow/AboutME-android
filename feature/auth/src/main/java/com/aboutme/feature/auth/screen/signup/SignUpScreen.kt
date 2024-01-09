package com.aboutme.feature.auth.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.core.ui.components.NameInfoDialog
import com.aboutme.core.ui.locals.LocalWindowSizeClass
import com.aboutme.core.ui.locals.ProvideWindowSizeClass
import com.aboutme.feature.auth.R
import com.aboutme.feature.auth.components.AuthBottomSection
import com.aboutme.feature.auth.components.AuthErrorSection
import com.aboutme.feature.auth.components.AuthScreen
import com.snow.core.input.component.OutlinedTextField
import com.snow.core.input.data.TextInput
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onContinue: (UserData) -> Unit,
    onGoToLogIn: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uiEvents = viewModel.uiEvents

    LaunchedEffect(true) {
        uiEvents.collectLatest {
            when (it) {
                is SignUpUiEvent.Continue -> onContinue(it.userData)
                SignUpUiEvent.GoToLogIn -> onGoToLogIn()
            }
        }
    }

    SignUpScreen(
        uiState = state.uiState,
        authState = state.authState,
        onEvent = viewModel::onEvent
    )

    if (state.uiState.showDialog) {
        NameInfoDialog(
            state.uiState.run {
                NameInfo(firstName.input, middleName.input, lastName.input, title.input)
            },
            onNameChange = {
                viewModel.onEvent(SignUpEvent.ChangeFirstName(it.firstName))
                viewModel.onEvent(SignUpEvent.ChangeMiddleName(it.middleName ?: ""))
                viewModel.onEvent(SignUpEvent.ChangeLastName(it.lastName ?: ""))
                viewModel.onEvent(SignUpEvent.ChangeTitle(it.title ?: ""))
                viewModel.onEvent(SignUpEvent.ToggleShowDialog)
            }
        )
    }
}

@Composable
private fun SignUpScreen(
    uiState: SignUpUiState,
    authState: SignUpAuthState,
    onEvent: (SignUpEvent) -> Unit
) {
    AuthScreen(
        modifier = Modifier.fillMaxSize()
    ) {
        SignUpScreenMobile(uiState, authState, onEvent)
    }
}

@Composable
private fun ColumnScope.SignUpScreenMobile(
    uiState: SignUpUiState,
    authState: SignUpAuthState,
    onEvent: (SignUpEvent) -> Unit
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
        text = stringResource(R.string.login_instaed),
        buttonText = stringResource(R.string.signup_title),
        onTextClick = { onEvent(SignUpEvent.ClickLogIn) },
        onButtonClick = { onEvent(SignUpEvent.SignUp) },
        tablet = tablet
    )
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
            text = stringResource(R.string.signup_title),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(R.string.signup_subtitle),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun CredentialsSection(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEvent: (SignUpEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement
            .spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = {
                onEvent(SignUpEvent.ChangeFirstName(it))
            },
            label = {
                Text(stringResource(R.string.name))
            },
            trailingIcon = {
                IconButton(
                    onClick = { onEvent(SignUpEvent.ToggleShowDialog) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.List,
                        contentDescription = stringResource(R.string.show_name_config)
                    )
                }
            }
        )
        OutlinedTextField(
            value = uiState.email,
            onValueChange = {
                onEvent(SignUpEvent.ChangeEmail(it))
            },
            label = {
                Text(text = stringResource(R.string.email))
            }
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = {
                onEvent(SignUpEvent.ChangePassword(it))
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onEvent(SignUpEvent.ToggleShowPassword)
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "SignUp Preview - Mobile",
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420"
)
@Composable
private fun SignUpScreenPreviewMobile() {
    ProvideWindowSizeClass(WindowSizeClass.calculateFromSize(DpSize(411.dp, 891.dp))) {
        MaterialTheme {
            Surface {
                SignUpScreen(
                    uiState = SignUpUiState(
                        email = TextInput("john@doe.com"),
                        password = TextInput("abcd1234"),
                        firstName = TextInput("John"),
                        middleName = TextInput("Peter"),
                        lastName = TextInput("Doe"),
                        title = TextInput("Dr."),
                        showPassword = true
                    ),
                    authState = SignUpAuthState.Error(SignUpError.Connection),
                    onEvent = { }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "SignUp Preview - Tablet",
    device = "spec:id=reference_tablet,shape=Normal,width=1280,height=800,unit=dp,dpi=240"
)
@Composable
private fun SignUpScreenPreviewTablet() {
    ProvideWindowSizeClass(WindowSizeClass.calculateFromSize(DpSize(1280.dp, 800.dp))) {
        MaterialTheme {
            Surface {
                SignUpScreen(
                    uiState = SignUpUiState(
                        email = TextInput("john@done.me"),
                        password = TextInput("abcd1234"),
                        firstName = TextInput("John"),
                        middleName = TextInput("Peter"),
                        lastName = TextInput("Doe"),
                        title = TextInput("Dr."),
                        showPassword = true
                    ),
                    authState = SignUpAuthState.Error(SignUpError.Connection),
                    onEvent = { }
                )
            }
        }
    }
}