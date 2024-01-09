package com.aboutme.core.ui.feed.dailydata

import androidx.annotation.StringRes
import com.aboutme.core.model.daily.DailyDataInfo

sealed class DailyDataFeedState {

    data object Loading : DailyDataFeedState()

    data class Error(
        @StringRes val description: Int
    ) : DailyDataFeedState()

    data class Success(
        val dailyDatas: List<DailyDataInfo<*>>
    ) : DailyDataFeedState()

}
