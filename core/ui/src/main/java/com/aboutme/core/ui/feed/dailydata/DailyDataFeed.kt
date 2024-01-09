package com.aboutme.core.ui.feed.dailydata

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.res.stringResource
import com.aboutme.core.model.daily.DailyDataInfo
import com.aboutme.core.ui.components.ErrorCard
import com.aboutme.core.ui.components.LabeledCircularProgressIndicator

/**
 * Constructs a daily datas feed from the given state
 *
 * @param state The [DailyDataFeedState]
 * @param onAction The callback invoked when any of the items has their action button clicked
 */
fun LazyGridScope.dailyDataFeed(
    state: DailyDataFeedState,
    onAction: (DailyDataInfo<*>) -> Unit
) {
    when (state) {
        is DailyDataFeedState.Error -> {
            item {
                ErrorCard(description = stringResource(state.description))
            }
        }

        DailyDataFeedState.Loading -> {
            item {
                LabeledCircularProgressIndicator()
            }
        }

        is DailyDataFeedState.Success -> {
            items(
                count = state.dailyDatas.size,
                key = { state.dailyDatas[it].category }
            ) { index ->
                DailyDataItem(
                    dailyData = state.dailyDatas[index],
                    onActionButton = {
                        onAction(state.dailyDatas[index])
                    }
                )
            }
        }
    }
}
