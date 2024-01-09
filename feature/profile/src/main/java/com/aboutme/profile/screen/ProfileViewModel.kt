package com.aboutme.profile.screen;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.AuthService
import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.model.Response
import com.aboutme.core.model.ResponseError
import com.aboutme.core.model.StreakInfo
import com.aboutme.core.ui.model.description
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepository: UserRepository
) : AboutMeViewModel<ProfileEvent, ProfileUiEvent, ProfileState>() {

    override val initialState = ProfileState()

    init {
        viewModelScope.launch {
            //We use refresh to get the user data. We really don't need to refresh here, but its a simple workaround.
            val response = authService.refresh()

            //TODO: Streaks are currently not implemented server-side. Needs to be done
            val streakInfo = StreakInfo(4, 11, 2, 3)

            if (response is Response.Error) {
                if (response.errors.first() == ResponseError.NotAuthorized) {
                    triggerUiEvent(ProfileUiEvent.GoToAuth)
                } else {
                    updateState {
                        it.copy(
                            userState = ProfileUserState.Error(response.errors.first().description)
                        )
                    }
                }
            } else if (response is Response.Success) {
                updateState {
                    it.copy(
                        userState = ProfileUserState.Success(
                            user = response.data.user,
                            streakInfo = streakInfo
                        )
                    )
                }
            }
        }
    }

    override fun handleEvent(event: ProfileEvent) = when (event) {
        ProfileEvent.ToggleNameInfoDialog -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    showNameChangeDialog = !it.uiState.showNameChangeDialog
                )
            )
        }

        ProfileEvent.ToggleDeleteProfileDialog -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    showDeleteProfileDialog = !it.uiState.showDeleteProfileDialog
                )
            )
        }

        is ProfileEvent.ChangeName -> handleChangeName(event)
        ProfileEvent.DeleteProfile -> handleDeleteProfile()
        ProfileEvent.Return -> triggerUiEvent(ProfileUiEvent.Return)
        ProfileEvent.GoToChangeEmail -> triggerUiEvent(ProfileUiEvent.GoToChangeEmail)
        ProfileEvent.GoToResetPassword -> triggerUiEvent(ProfileUiEvent.GoToResetPassword)
        ProfileEvent.LogOut -> handleLogOut()
        ProfileEvent.LogOutAll -> handleLogOutAll()
    }

    private fun handleLogOutAll() {
        triggerUiEvent(ProfileUiEvent.GoToAuth)
        viewModelScope.launch {
            authService.logOutAll()
        }
    }

    private fun handleLogOut() {
        triggerUiEvent(ProfileUiEvent.GoToAuth)
        viewModelScope.launch {
            authService.logOut()
        }
    }

    private fun handleDeleteProfile() {
        triggerUiEvent(ProfileUiEvent.GoToAuth)
        viewModelScope.launch {
            userRepository.deleteUser()
        }
    }

    private fun handleChangeName(event: ProfileEvent.ChangeName) {
        viewModelScope.launch {
            val response = userRepository.updateUser(event.nameInfo)
            if (response is Response.Success) {
                updateState {
                    it.copy(
                        userState = ProfileUserState.Success(response.data, StreakInfo(4, 11, 2, 3)),
                        uiState = it.uiState.copy(showNameChangeDialog = false)
                    )
                }
            }
        }
    }
}