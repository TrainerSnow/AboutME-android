package com.aboutme.feature.auth.screen.signup

import androidx.annotation.StringRes
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aboutme.core.model.data.UserData
import com.aboutme.feature.auth.R
import com.aboutme.feature.auth.components.AuthState
import com.snow.core.input.data.TextInput

internal data class SignUpState(

    val uiState: SignUpUiState = SignUpUiState(),

    val authState: SignUpAuthState = SignUpAuthState.None

)

internal data class SignUpUiState(

    val email: TextInput = TextInput(),

    val password: TextInput = TextInput(),

    val firstName: TextInput = TextInput(),

    val middleName: TextInput = TextInput(),

    val lastName: TextInput = TextInput(),

    val title: TextInput = TextInput(),

    val showPassword: Boolean = false,

    val showDialog: Boolean = false

)

internal sealed class SignUpAuthState {

    data object None : SignUpAuthState()

    data object Loading: SignUpAuthState()

    data class Error(val error: SignUpError): SignUpAuthState()

    data class Success(val userData: UserData) : SignUpAuthState()

    @Composable
    fun toAuthState(): AuthState = when (this) {
        is Error -> AuthState.Error(stringResource(error.localizedDescription()))
        Loading -> AuthState.Loading
        else -> AuthState.None
    }

}

internal sealed class SignUpError {

    data object Connection : SignUpError()

    data object Unknown: SignUpError()

    @StringRes
    fun localizedDescription(): Int = when (this) {
        Connection -> R.string.signup_error_connection
        Unknown -> R.string.signup_error_unknown
    }

}