package com.aboutme.profile.screen

import com.aboutme.core.model.data.NameInfo

internal sealed interface ProfileEvent {

    data object ToggleNameInfoDialog : ProfileEvent
    data class ChangeName(val nameInfo: NameInfo) : ProfileEvent

    data object ToggleDeleteProfileDialog : ProfileEvent
    data object DeleteProfile : ProfileEvent

    data object LogOut : ProfileEvent
    data object LogOutAll : ProfileEvent

    data object GoToResetPassword : ProfileEvent

    data object GoToChangeEmail : ProfileEvent

    data object Return: ProfileEvent
}

internal sealed interface ProfileUiEvent {

    data object GoToAuth : ProfileUiEvent

    data object GoToResetPassword : ProfileUiEvent

    data object GoToChangeEmail : ProfileUiEvent

    data object Return: ProfileUiEvent
}