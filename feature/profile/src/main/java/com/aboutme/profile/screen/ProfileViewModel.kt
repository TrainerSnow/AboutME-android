package com.aboutme.profile.screen;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.AuthService
import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.model.StreakInfo
import com.aboutme.profile.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    val userRepository: UserRepository,
    val authService: AuthService
) : AboutMeViewModel<ProfileEvent, ProfileUiEvent, ProfileState>() {

    override val initialState = ProfileState()

    val profileState: StateFlow<ProfileUserState> = userRepository
        .getUser()
        .map {
            if(it == null) ProfileUserState.Loading
            else ProfileUserState.Success(it, StreakInfo(4, 11, 2, 3))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileUserState.Loading
        )

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
            authService.deleteUser()
        }
    }

    private fun handleChangeName(event: ProfileEvent.ChangeName) {
        viewModelScope.launch {
            userRepository.updateUser(event.nameInfo)
        }
    }
}