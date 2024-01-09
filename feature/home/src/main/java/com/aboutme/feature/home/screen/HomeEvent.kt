package com.aboutme.feature.home.screen

sealed interface HomeEvent {

    data object ToggleUserPopup : HomeEvent

    data object LogOut : HomeEvent

    data object LogOutAll : HomeEvent

    data object GoToProfile : HomeEvent

}

sealed class HomeUiEvent {

    data object AuthError : HomeUiEvent()

    data object LogOut: HomeUiEvent()

    data object GoToProfile: HomeUiEvent()

}