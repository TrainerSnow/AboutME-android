package com.aboutme.feature.auth.screen.login

import com.aboutme.core.model.data.AuthUser

sealed class LogInEvent {

    data class ChangeEmail(val email: String): LogInEvent()

    data class ChangePassword(val password: String): LogInEvent()

    data object ToggleShowPassword: LogInEvent()

    data object LogIn: LogInEvent()

    data object GoToSignUp: LogInEvent()

}

sealed class LogInUiEvent {

    data class Continue(val authUser: AuthUser): LogInUiEvent()

    data object GoToSignUp: LogInUiEvent()

}