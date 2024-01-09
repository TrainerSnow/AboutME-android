package com.aboutme.feature.auth.screen.signup;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.common.takeAsInput
import com.aboutme.core.data.AuthService
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.model.Response
import com.aboutme.core.model.ResponseError
import com.aboutme.core.model.data.NameInfo
import com.snow.core.input.createForEmail
import com.snow.core.input.createForName
import com.snow.core.input.createForPassword
import com.snow.core.input.error.InputError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    val authService: AuthService
) : AboutMeViewModel<SignUpEvent, SignUpUiEvent, SignUpState>() {

    override val initialState = SignUpState()

    override fun handleEvent(event: SignUpEvent) = when (event) {
        is SignUpEvent.ChangeEmail -> updateState {
            val correctedError = !event.email.createForEmail().isError()
            it.copy(
                uiState = it.uiState.copy(
                    email = it.uiState.email.copy(
                        input = event.email,
                        error = if (correctedError) null
                        else it.uiState.email.error
                    )
                )
            )
        }

        is SignUpEvent.ChangeFirstName -> updateState {
            val correctedError = !event.firstName.createForName().isError()
            it.copy(
                uiState = it.uiState.copy(
                    firstName = it.uiState.firstName.copy(
                        input = event.firstName,
                        if (correctedError) null
                        else it.uiState.firstName.error
                    )
                )
            )
        }

        is SignUpEvent.ChangeLastName -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    lastName = it.uiState.lastName.copy(
                        input = event.lastName
                    )
                )
            )
        }

        is SignUpEvent.ChangeMiddleName -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    middleName = it.uiState.middleName.copy(
                        input = event.middleName
                    )
                )
            )
        }

        is SignUpEvent.ChangePassword -> updateState {
            val correctedError = !event.password.createForPassword().isError()
            it.copy(
                uiState = it.uiState.copy(
                    password = it.uiState.password.copy(
                        input = event.password,
                        error = if (correctedError) null
                        else it.uiState.password.error
                    )
                )
            )
        }

        is SignUpEvent.ChangeTitle -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    title = it.uiState.title.copy(
                        input = event.title
                    )
                )
            )
        }

        SignUpEvent.ToggleShowPassword -> {
            updateState {
                it.copy(
                    uiState = it.uiState.copy(
                        showPassword = !it.uiState.showPassword
                    )
                )
            }
        }

        SignUpEvent.ClickLogIn -> triggerUiEvent(SignUpUiEvent.GoToLogIn)
        SignUpEvent.SignUp -> handleSignUp()
        SignUpEvent.ToggleShowDialog -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    showDialog = !it.uiState.showDialog
                )
            )
        }
    }

    private fun handleSignUp() {
        val email = state.value.uiState.email.input.createForEmail()
        val firstName = state.value.uiState.firstName.input.createForName()
        val password = state.value.uiState.password.input.createForPassword()

        val isAnyError =
            email.isError() || firstName.isError() || password.isError()

        if (isAnyError) {
            updateState {
                it.copy(
                    uiState = it.uiState.copy(
                        email = email,
                        firstName = firstName,
                        password = password
                    )
                )
            }
            return
        }

        updateState {
            it.copy(
                authState = SignUpAuthState.Loading
            )
        }

        viewModelScope.launch {
            val result = authService.signUp(
                email = state.value.uiState.email.input,
                password = state.value.uiState.password.input,
                nameInfo = state.value.uiState.run {
                    NameInfo(
                        firstName.input,
                        middleName.input.takeAsInput(),
                        lastName.input.takeAsInput(),
                        title.input.takeAsInput()
                    )
                }
            )

            if (result is Response.Success) {
                updateState {
                    it.copy(
                        authState = SignUpAuthState.Success(result.data.user)
                    )
                }
                triggerUiEvent(SignUpUiEvent.Continue(result.data.user))
            } else {
                updateState {
                    it.copy(
                        authState = when (result) {
                            is Response.Error -> when (result.errors.first()) {
                                ResponseError.Conflict -> SignUpAuthState.None
                                ResponseError.Network -> SignUpAuthState.Error(
                                    SignUpError.Connection
                                )

                                else -> SignUpAuthState.Error(SignUpError.Unknown)
                            }

                            else -> SignUpAuthState.Error(SignUpError.Unknown)
                        },
                        uiState = it.uiState.copy(
                            email = if (result is Response.Error) {
                                if (result.errors.first() == ResponseError.Conflict) {
                                    it.uiState.email.copy(error = InputError.EmailTaken)
                                } else it.uiState.email
                            } else it.uiState.email
                        )
                    )
                }
            }
        }
    }

}