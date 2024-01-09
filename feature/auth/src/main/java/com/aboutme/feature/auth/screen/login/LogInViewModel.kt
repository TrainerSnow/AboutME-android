package com.aboutme.feature.auth.screen.login;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.AuthService
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.model.Response
import com.aboutme.core.model.ResponseError
import com.snow.core.input.createForEmail
import com.snow.core.input.data.TextInput
import com.snow.core.input.error.InputError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LogInViewModel @Inject constructor(
    val authService: AuthService
) : AboutMeViewModel<LogInEvent, LogInUiEvent, LogInState>() {

    override val initialState = LogInState()

    override fun handleEvent(event: LogInEvent) = when (event) {
        is LogInEvent.ChangeEmail -> updateState {
            val correctedError = !event.email.createForEmail().isError()
            it.copy(
                uiState = it.uiState.copy(
                    email = TextInput(
                        input = event.email,
                        error = if (correctedError) null
                        else it.uiState.email.error
                    )
                )
            )
        }

        is LogInEvent.ChangePassword -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    password = it.uiState.password.copy(input = event.password)
                )
            )
        }

        LogInEvent.GoToSignUp -> triggerUiEvent(LogInUiEvent.GoToSignUp)

        LogInEvent.ToggleShowPassword -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    showPassword = !it.uiState.showPassword
                )
            )
        }

        LogInEvent.LogIn -> handleLogIn()
    }

    private fun handleLogIn() {
        val email = state.value.uiState.email.input.createForEmail()

        if (email.isError()) {
            updateState {
                it.copy(
                    uiState = it.uiState.copy(
                        email = email
                    )
                )
            }
            return
        }

        updateState {
            it.copy(
                authState = LogInAuthState.Loading
            )
        }

        viewModelScope.launch {
            val result = authService.logIn(
                state.value.uiState.email.input,
                state.value.uiState.password.input
            )

            if (result is Response.Success) {
                updateState {
                    it.copy(
                        authState = LogInAuthState.Success(result.data)
                    )
                }
                triggerUiEvent(LogInUiEvent.Continue(result.data))
            } else if (result is Response.Error) {
                updateState {
                    it.copy(
                        authState = if (result.errors.first() == ResponseError.Network)
                            LogInAuthState.Error(LogInError.Connection)
                        else if (result.errors.first() == ResponseError.Unknown)
                            LogInAuthState.Error(LogInError.Unknown)
                        else
                            LogInAuthState.None,
                        uiState = if (result.errors.first() == ResponseError.NotAuthorized)
                            it.uiState.copy(
                                email = it.uiState.email.copy(error = InputError.InvalidCredential),
                                password = it.uiState.password.copy(error = InputError.InvalidCredential)
                            )
                        else
                            it.uiState
                    )
                }
            }
        }
    }

}