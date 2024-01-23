package com.aboutme.core.ui.feed.syncresult

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.core.model.sync.SyncTrafficInfo
import com.aboutme.core.ui.components.LabeledCircularProgressIndicator
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.random.Random

fun LazyListScope.syncResultFeed(
    state: SyncResultFeedState,
    onClick: (SyncResult.Success) -> Unit
) {
    when (state) {
        SyncResultFeedState.Loading -> {
            item(
                key = ContentTypeLoading,
                contentType = ContentTypeLoading
            ) {
                Box(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillParentMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LabeledCircularProgressIndicator()
                }
            }
        }

        is SyncResultFeedState.Success -> {
            items(
                count = state.syncResults.size,
                key = {
                    state.syncResults[it].started
                },
                contentType = {
                    if (state.syncResults[it] is SyncResult.Success) ContentTypeSuccess else ContentTypeError
                }
            ) {
                SyncResultItem(
                    onClick = onClick,
                    syncResult = state.syncResults[it]
                )
            }
        }
    }
}

private const val ContentTypeError = "com.aboutme.core.ui.feed.syncresult.Error"
private const val ContentTypeSuccess = "com.aboutme.core.ui.feed.syncresult.Success"
private const val ContentTypeLoading = "com.aboutme.core.ui.feed.syncresult.Loading"

@Preview
@Composable
private fun SyncResultFeedPreview() {
    val rand = Random.Default

    val results = (1..12).map { index ->
        val start = Instant.now().minus(index.toLong(), ChronoUnit.DAYS)
        val end = Instant.now().minus(index.toLong(), ChronoUnit.DAYS).plusSeconds(60L * index)
        if (rand.nextFloat() > 0.6F) {
            SyncResult.NotAuthorized(start, end)
        } else {
            SyncResult.Success(
                started = start,
                ended = end,
                SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                SyncTrafficInfo(0, 0, 0, 0, 0, 0),
                SyncTrafficInfo(0, 0, 0, 0, 0, 0)
            )
        }
    }

    Surface {
        LazyColumn(
            modifier = Modifier
                .width(400.dp)
        ) {
            syncResultFeed(
                state = SyncResultFeedState.Success(results),
                onClick = { }
            )
        }
    }
}

@Preview
@Composable
private fun SyncResultFeedPreviewLoading() {
    Surface {
        LazyColumn(
            modifier = Modifier
                .width(400.dp)
        ) {
            syncResultFeed(
                state = SyncResultFeedState.Loading,
                onClick = { }
            )
        }
    }
}