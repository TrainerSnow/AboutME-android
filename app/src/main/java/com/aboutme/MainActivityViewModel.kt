package com.aboutme;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.AuthService
import com.aboutme.core.model.Response
import com.aboutme.core.model.data.AuthUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val authService: AuthService
) : ViewModel() {

    val state: MutableStateFlow<MainActivityState> = MutableStateFlow(MainActivityState.Loading)

    init {
        viewModelScope.launch {
            val result = authService.refresh()
            if (result is Response.Success) {
                state.emit(MainActivityState.Authenticated(result.data))
            } else {
                state.emit(MainActivityState.NonAuthenticated)
            }
        }
    }

}

sealed interface MainActivityState {

    data object Loading : MainActivityState

    data object NonAuthenticated : MainActivityState

    data class Authenticated(
        val authUser: AuthUser
    ) : MainActivityState

}