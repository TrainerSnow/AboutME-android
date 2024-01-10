package com.aboutme.feature.home.screen;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.AuthService
import com.aboutme.core.data.repository.DailyDataRepository
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.sync.SyncController
import com.aboutme.core.ui.feed.dailydata.DailyDataFeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    dailyDataRepository: DailyDataRepository,
    val authService: AuthService,
    syncController: SyncController
) : AboutMeViewModel<HomeEvent, HomeUiEvent, HomeState>() {

    init {
        syncController.syncNow()
    }

    override val initialState = HomeState()

    val dailyFeedState: StateFlow<DailyDataFeedState> =
        dailyDataRepository.getForDay(LocalDate.now())
            .map {
                DailyDataFeedState.Success(it.all().toList())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = DailyDataFeedState.Loading
            )

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

}