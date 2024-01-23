package com.aboutme.core.ui.feed.syncresult

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboutme.core.common.time.durationTo
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.core.model.sync.SyncTrafficInfo
import com.aboutme.core.ui.R
import com.aboutme.core.ui.time.format
import com.aboutme.core.ui.time.formatDateFirst
import com.aboutme.core.ui.time.toDeviceDate
import java.time.Instant

@Composable
fun SyncResultItem(
    modifier: Modifier = Modifier,
    onClick: (SyncResult.Success) -> Unit,
    syncResult: SyncResult
) {
    when (syncResult) {
        is SyncResult.NotAuthorized -> ErrorSyncResultItem(modifier, syncResult)
        is SyncResult.Success -> SuccessSyncResultItem(modifier, onClick, syncResult)
    }
}

@Composable
private fun SuccessSyncResultItem(
    modifier: Modifier = Modifier,
    onClick: (SyncResult.Success) -> Unit,
    syncResult: SyncResult.Success
) {
    val headline = stringResource(
        R.string.sync_result_feed_success_headline,
        syncResult.started.toDeviceDate().formatDateFirst()
    )

    ListItem(
        modifier = modifier
            .clickable {
                onClick(syncResult)
            },
        headlineContent = {
            Text(
                text = headline
            )
        },
        supportingContent = {
            Column {
                Text(
                    text = stringResource(R.string.sync_result_feed_success_supporting)
                )
                Text(
                    text = stringResource(
                        R.string.sync_result_feed_success_timing,
                        syncResult.started.durationTo(syncResult.ended).format()
                    ),
                    style = LocalTextStyle.current.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        },
        leadingContent = {
            Image(
                modifier = Modifier
                    .size(64.dp),
                painter = painterResource(R.drawable.sync_result_feed_leading_success),
                contentDescription = null
            )
        }
    )
}

@Composable
private fun ErrorSyncResultItem(
    modifier: Modifier = Modifier,
    syncResult: SyncResult.NotAuthorized
) {
    val headline = stringResource(
        R.string.sync_result_feed_error_headline,
        syncResult.started.toDeviceDate().formatDateFirst()
    )

    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = headline
            )
        },
        supportingContent = {
            Text(
                text = stringResource(R.string.sync_result_feed_error_supporting)
            )
        },
        leadingContent = {
            Image(
                modifier = Modifier
                    .size(64.dp),
                painter = painterResource(R.drawable.sync_result_feed_leading_error),
                contentDescription = null
            )
        }
    )
}

@Preview
@Composable
private fun SyncResultItemPreviewError() {
    ErrorSyncResultItem(
        syncResult = SyncResult.NotAuthorized(Instant.now(), Instant.now())
    )
}

@Preview
@Composable
private fun SyncResultItemPreviewSuccess() {
    SuccessSyncResultItem(
        onClick = { },
        syncResult = SyncResult.Success(
            started = Instant.now().minusSeconds(240),
            ended = Instant.now(),
            SyncTrafficInfo(0, 0, 0, 0, 0, 0),
            SyncTrafficInfo(0, 0, 0, 0, 0, 0),
            SyncTrafficInfo(0, 0, 0, 0, 0, 0),
            SyncTrafficInfo(0, 0, 0, 0, 0, 0),
            SyncTrafficInfo(0, 0, 0, 0, 0, 0),
            SyncTrafficInfo(0, 0, 0, 0, 0, 0),
            SyncTrafficInfo(0, 0, 0, 0, 0, 0),
            SyncTrafficInfo(0, 0, 0, 0, 0, 0)
        )
    )
}