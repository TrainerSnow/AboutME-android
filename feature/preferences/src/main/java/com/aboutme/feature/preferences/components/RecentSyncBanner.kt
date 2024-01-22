package com.aboutme.feature.preferences.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboutme.core.common.time.durationTo
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.core.ui.time.format
import com.aboutme.core.ui.time.formatDateFirst
import com.aboutme.core.ui.time.toDeviceDate
import com.aboutme.core.ui.util.errorCardColors
import com.aboutme.feature.preferences.R
import java.time.Instant

@Composable
internal fun RecentSyncBanner(
    modifier: Modifier = Modifier,
    syncInfo: SyncResult,
    onSyncNow: () -> Unit,
    onAuth: () -> Unit,
    onMoreInfo: (SyncResult.Success) -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        colors = if (syncInfo is SyncResult.NotAuthorized) CardDefaults.errorCardColors() else CardDefaults.elevatedCardColors()
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            if (syncInfo is SyncResult.NotAuthorized) {
                SyncErrorResultContent(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onAuthClick = onAuth,
                    syncResult = syncInfo
                )
            } else if (syncInfo is SyncResult.Success) {
                SyncSuccessResultContent(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onSyncNow = onSyncNow,
                    syncResult = syncInfo,
                    onMoreInfo = onMoreInfo
                )
            }
        }
    }
}

@Composable
private fun SyncErrorResultContent(
    modifier: Modifier = Modifier,
    onAuthClick: () -> Unit,
    syncResult: SyncResult.NotAuthorized
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.sync_preferences_recent_sync_fail),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(
                R.string.sync_preferences_recent_sync_failed_info,
                syncResult.started.toDeviceDate().formatDateFirst()
            ),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            modifier = Modifier
                .align(Alignment.End),
            onClick = onAuthClick
        ) {
            Text(
                text = stringResource(R.string.sync_preferences_relogin)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SyncSuccessResultContent(
    modifier: Modifier = Modifier,
    onSyncNow: () -> Unit,
    onMoreInfo: (SyncResult.Success) -> Unit,
    syncResult: SyncResult.Success
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.sync_preferences_recent_sync_headline),
            style = MaterialTheme.typography.titleMedium
        )
        FlowRow {
            Text(
                text = stringResource(
                    R.string.sync_preferences_recent_sync_info,
                    syncResult.ended.toDeviceDate().formatDateFirst(),
                    syncResult.started.durationTo(syncResult.ended).format()
                ),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = { onMoreInfo(syncResult) }
                    ),
                text = stringResource(R.string.sync_preferences_recent_sync_more),
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline
            )
        }
        Button(
            modifier = Modifier
                .align(Alignment.End),
            onClick = onSyncNow
        ) {
            Text(
                text = stringResource(R.string.sync_preferences_sync_now)
            )
        }
    }
}

@Preview
@Composable
private fun RecentSyncBannerPreview() {
    Surface {
        RecentSyncBanner(
            syncInfo = SyncResult.NotAuthorized(Instant.now().minusSeconds(240), Instant.now()),
            onSyncNow = { },
            onMoreInfo = { },
            onAuth = { }
        )
    }
}