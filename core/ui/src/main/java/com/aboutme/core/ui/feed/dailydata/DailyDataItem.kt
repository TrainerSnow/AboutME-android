package com.aboutme.core.ui.feed.dailydata

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboutme.core.model.daily.DailyDataCategory
import com.aboutme.core.model.daily.DailyDataInfo
import com.aboutme.core.ui.R
import com.aboutme.core.ui.locals.LocalWindowSizeClass
import com.aboutme.core.ui.model.description
import com.aboutme.core.ui.model.illustration
import com.aboutme.core.ui.model.title
import com.aboutme.core.ui.preview.AboutMePreview
import com.aboutme.core.ui.util.from
import java.time.LocalDate

/**
 * An item to display Information about the daily data
 */
@Composable
fun <DailyData> DailyDataItem(
    modifier: Modifier = Modifier,
    dailyData: DailyDataInfo<DailyData>,
    onActionButton: () -> Unit = {}
) {
    val widthSizeClass = LocalWindowSizeClass.current.widthSizeClass

    if (widthSizeClass > WindowWidthSizeClass.Compact) {
        DailyDataItemWide(modifier, dailyData, onActionButton)
    } else {
        DailyDataItemThin(modifier, dailyData, onActionButton)
    }
}

@Composable
fun <DailyData> DailyDataItemThin(
    modifier: Modifier = Modifier,
    dailyData: DailyDataInfo<DailyData>,
    onActionButton: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(dailyData.category.illustration),
                contentDescription = stringResource(dailyData.category.title)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(dailyData.category.title),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium)
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(dailyData.category.description),
                    style = MaterialTheme.typography.bodyMedium
                )
                StatusSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    completed = dailyData.data != null
                )
                ButtonSection(
                    modifier = Modifier.align(Alignment.End),
                    completed = dailyData.data != null,
                    onClick = onActionButton
                )
            }
        }
    }
}

@Composable
fun <DailyData> DailyDataItemWide(
    modifier: Modifier = Modifier,
    dailyData: DailyDataInfo<DailyData>,
    onActionButton: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .weight(1F),
                painter = painterResource(dailyData.category.illustration),
                contentDescription = stringResource(dailyData.category.title)
            )
            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(dailyData.category.title),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium)
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(dailyData.category.description),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                StatusSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    completed = dailyData.data != null
                )
            }
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            ButtonSection(
                completed = dailyData.data != null,
                onClick = onActionButton
            )
        }
    }
}

@Composable
private fun ButtonSection(
    modifier: Modifier = Modifier,
    completed: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = onClick
        ) {
            Text(
                text = stringResource(
                    id = if (completed) R.string.daily_data_item_button_completed
                    else R.string.daily_data_item_button_uncompleted
                )
            )
        }
    }
}

@Composable
private fun StatusSection(
    modifier: Modifier = Modifier,
    completed: Boolean
) {
    val color = if (completed)
        Color.Green
    else MaterialTheme.colorScheme.error //TODO: Use proper material branded colors instead of this

    val icon = if (completed) Icons.Outlined.Check else Icons.Outlined.Warning

    val text = if (completed)
        stringResource(R.string.daily_data_item_comleted_title)
    else
        stringResource(R.string.daily_data_item_uncomplted_title)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = color
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                color = color
            )
        )
    }
}

@Preview(widthDp = 1000)
@Composable
private fun DailyDataItemPreviewCompleted() =
    AboutMePreview("Daily Data Item [Completed|Comppact]") {
        DailyDataItem(
            modifier = Modifier
                .width(350.dp),
            dailyData = DailyDataInfo(
                data = Unit,
                date = LocalDate.now(),
                category = DailyDataCategory.DreamData
            )
        )
    }

@Preview(widthDp = 1000)
@Composable
private fun DailyDataItemPreviewUncompleted() =
    AboutMePreview("Daily Data Item [Uncompleted|Comppact]") {
        DailyDataItem(
            modifier = Modifier
                .width(350.dp),
            dailyData = DailyDataInfo(
                data = null,
                date = LocalDate.now(),
                category = DailyDataCategory.DreamData
            )
        )
    }

@Preview(widthDp = 1500)
@Composable
private fun DailyDataItemPreviewCompletedWide() = AboutMePreview(
    name = "Daily Data Item [Completed|Medium-Expanded]",
    windowSizeClass = WindowSizeClass.from(
        WindowWidthSizeClass.Expanded,
        WindowHeightSizeClass.Expanded
    )
) {
    DailyDataItem(
        modifier = Modifier
            .width(600.dp),
        dailyData = DailyDataInfo(
            data = Unit,
            date = LocalDate.now(),
            category = DailyDataCategory.DreamData
        )
    )
}

@Preview(widthDp = 1500)
@Composable
private fun DailyDataItemPreviewUncompletedWide() = AboutMePreview(
    name = "Daily Data Item [Completed|Medium-Expanded]",
    windowSizeClass = WindowSizeClass.from(
        WindowWidthSizeClass.Expanded,
        WindowHeightSizeClass.Expanded
    )
) {
    DailyDataItem(
        modifier = Modifier
            .width(600.dp),
        dailyData = DailyDataInfo(
            data = null,
            date = LocalDate.now(),
            category = DailyDataCategory.DreamData
        )
    )
}