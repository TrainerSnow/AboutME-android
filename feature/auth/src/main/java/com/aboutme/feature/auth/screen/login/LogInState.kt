package com.aboutme.feature.auth.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aboutme.core.model.data.AuthUser
import com.aboutme.feature.auth.R
import com.aboutme.feature.auth.components.AuthState
import com.snow.core.input.data.TextInput

internal data class LogInState(

    val uiState: LogInUiState = LogInUiState(),

    val authState: LogInAuthState = LogInAuthState.None

)

internal data class LogInUiState(

    val email: TextInput = TextInput(),

    val password: TextInput = TextInput(),

    val showPassword: Boolean = false

)

internal sealed class LogInAuthState {

    data object None : LogInAuthState()

    data object Loading : LogInAuthState()

    data class Error(val error: LogInError) : LogInAuthState()

    data class Success(val authUser: AuthUser) : LogInAuthState()

    @Composable
    fun toAuthState(): AuthState = when (this) {
        is Error -> AuthState.Error(stringResource(error.localizedMessage()))
        Loading -> AuthState.Loading
        else -> AuthState.None
    }

}

internal sealed class LogInError {

    data object Connection : LogInError()

    data object Unknown : LogInError()

    fun localizedMessage() = when (this) {
        Connection -> R.string.login_error_connection
        Unknown -> R.string.login_error_unknown
    }

}