package com.aboutme.feature.home.screen

import com.aboutme.core.model.sort.SortConfig
import com.aboutme.core.ui.feed.dailydata.DailyDataFeedState

data class HomeState(

    val uiState: HomeUiState = HomeUiState()

)

data class HomeUiState (

    val showUserPopup: Boolean = false,

    val sortConfig: SortConfig = SortConfig.None,

)
