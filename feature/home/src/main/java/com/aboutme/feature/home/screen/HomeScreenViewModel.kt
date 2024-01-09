package com.aboutme.feature.home.screen;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.AuthService
import com.aboutme.core.data.repository.DailyDataRepository
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.model.Response
import com.aboutme.core.model.sort.sortBy
import com.aboutme.core.ui.feed.dailydata.DailyDataFeedState
import com.aboutme.core.ui.model.description
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    dailyDataRepository: DailyDataRepository,
    val authService: AuthService
) : AboutMeViewModel<HomeEvent, HomeUiEvent, HomeState>() {

    override val initialState = HomeState()

    override fun handleEvent(event: HomeEvent) = when (event) {
        HomeEvent.ToggleUserPopup -> updateState {
            it.copy(
                uiState = it.uiState.copy(
                    showUserPopup = !it.uiState.showUserPopup
                )
            )
        }

        HomeEvent.GoToProfile -> triggerUiEvent(HomeUiEvent.GoToProfile)

        HomeEvent.LogOut -> handleLogOut()

        HomeEvent.LogOutAll -> handleLogOutAll()
    }

    private fun handleLogOut() {
        triggerUiEvent(HomeUiEvent.LogOut)
        viewModelScope.launch {
            authService.logOut()
        }
    }

    private fun handleLogOutAll() {
        triggerUiEvent(HomeUiEvent.LogOut)
        viewModelScope.launch {
            authService.logOutAll()
        }
    }

    init {
        viewModelScope.launch {
            val result = dailyDataRepository.getForDay(LocalDate.now())

            if (result is Response.Success) {
                updateState {
                    it.copy(
                        dailyFeedState = DailyDataFeedState.Success(result.data.all().toList())
                    )
                }
            } else if (result is Response.Error) {
                updateState {
                    it.copy(
                        dailyFeedState = DailyDataFeedState.Error(result.errors.first().description)
                    )
                }
            }
        }
    }

}