package com.aboutme.feature.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aboutme.core.model.daily.DailyDataProgress
import com.aboutme.core.ui.locals.LocalWindowSizeClass
import com.aboutme.feature.home.R

@Composable
internal fun HomeHeader(
    modifier: Modifier = Modifier,
    progress: DailyDataProgress,
    onPersonsClick: () -> Unit
) {
    val doVertical = LocalWindowSizeClass.current.widthSizeClass == WindowWidthSizeClass.Compact

    if (doVertical) {
        HomeHeaderVertical(modifier, progress, onPersonsClick)
    } else {
        HomeHeaderHorizontal(modifier, progress, onPersonsClick)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HomeHeaderVertical(
    modifier: Modifier = Modifier,
    progress: DailyDataProgress,
    onPersonsClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProgressCard(
            modifier = Modifier,
            progress = progress
        )
        FlowRow {
            HomeHeaderLink(
                onClick = onPersonsClick,
                title = stringResource(R.string.home_link_persons_title),
                summary = stringResource(R.string.home_link_persons_summary),
                icon = Icons.Outlined.Person
            )
        }
    }
}

@Composable
private fun HomeHeaderHorizontal(
    modifier: Modifier = Modifier,
    progress: DailyDataProgress,
    onPersonsClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProgressCard(
            modifier = Modifier
                .fillMaxWidth(),
            progress = progress
        )
        Column {
            HomeHeaderLink(
                onClick = onPersonsClick,
                title = stringResource(R.string.home_link_persons_title),
                summary = stringResource(R.string.home_link_persons_summary),
                icon = Icons.Outlined.Person
            )
        }
    }
}