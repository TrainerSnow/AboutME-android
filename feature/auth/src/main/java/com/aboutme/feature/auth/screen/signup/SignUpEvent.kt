package com.aboutme.feature.auth.screen.signup

import com.aboutme.core.model.data.UserData

internal sealed class SignUpEvent {

    data class ChangeEmail(val email: String) : SignUpEvent()

    data class ChangePassword(val password: String) : SignUpEvent()

    data class ChangeFirstName(val firstName: String) : SignUpEvent()

    data class ChangeMiddleName(val middleName: String) : SignUpEvent()

    data class ChangeLastName(val lastName: String) : SignUpEvent()

    data class ChangeTitle(val title: String) : SignUpEvent()

    data object SignUp: SignUpEvent()

    data object ClickLogIn: SignUpEvent()

    data object ToggleShowPassword: SignUpEvent()

    data object ToggleShowDialog: SignUpEvent()

}

internal sealed class SignUpUiEvent {

    data object GoToLogIn: SignUpUiEvent()

    data class Continue(val userData: UserData): SignUpUiEvent()

}