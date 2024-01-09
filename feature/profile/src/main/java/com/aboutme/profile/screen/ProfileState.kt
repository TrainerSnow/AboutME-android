package com.aboutme.profile.screen

import androidx.annotation.StringRes
import com.aboutme.core.model.StreakInfo
import com.aboutme.core.model.data.UserData

internal data class ProfileState(

    val userState: ProfileUserState = ProfileUserState.Loading,

    val uiState: ProfileUiState = ProfileUiState()

)

internal data class ProfileUiState (

    val showNameChangeDialog: Boolean = false,

    val showDeleteProfileDialog: Boolean = false

)

internal sealed interface ProfileUserState {

    data object Loading : ProfileUserState

    data class Error(@StringRes val msg: Int): ProfileUserState

    data class Success(
        val user: UserData,
        val streakInfo: StreakInfo
    ): ProfileUserState

}