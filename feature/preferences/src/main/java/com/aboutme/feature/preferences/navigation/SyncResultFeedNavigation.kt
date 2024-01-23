package com.aboutme.feature.preferences.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.feature.preferences.screen.syncresult.feed.SyncResultFeedScreen

internal const val SyncResultFeedRoute = "sync_result_feed"

internal fun NavController.navigateToSyncResultFeed(navOptions: NavOptions? = null) = navigate(
    SyncResultFeedRoute, navOptions
)

internal fun NavGraphBuilder.syncResultFeed(
    onReturn: () -> Unit,
    onGoToDetail: (SyncResult.Success) -> Unit
) {
    composable(
        route = SyncResultFeedRoute
    ) {
        SyncResultFeedScreen(
            onReturn = onReturn,
            onGoToDetail = onGoToDetail
        )
    }
}