package com.aboutme;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.model.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val userRepo: UserRepository
) : ViewModel() {

    val state: MutableStateFlow<MainActivityState> = MutableStateFlow(MainActivityState.Loading)

    init {
        viewModelScope.launch {
            val user = userRepo.getUser().first()
            if (user != null) {
                state.emit(MainActivityState.Authenticated(user))
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
        val userData: UserData
    ) : MainActivityState

}