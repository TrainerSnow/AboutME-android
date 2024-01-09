package com.aboutme.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboutme.core.model.sort.SortConfig
import com.aboutme.core.model.sort.SortDirection
import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.ui.R
import com.aboutme.core.ui.label
import com.aboutme.core.ui.preview.AboutMePreview

@Composable
fun SortDialog(
    sortConfig: SortConfig = SortConfig.None,
    sortModes: Set<SortMode> = SortMode.entries.toSet(),
    onSortChange: (SortConfig) -> Unit
) {
    var sort by remember { mutableStateOf(sortConfig) }

    AlertDialog(
        onDismissRequest = { onSortChange(sortConfig) },
        confirmButton = {
            TextButton(
                onClick = {
                    onSortChange(sort)
                }
            ) {
                Text(
                    text = stringResource(android.R.string.ok)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.sort_dialog_title)
            )
        },
        text = {
            SortSection(
                sortConfig = sort,
                sortModes = sortModes,
                onSortChange = { sort = it }
            )
        }
    )
}

@Composable
fun SortCard(
    modifier: Modifier = Modifier,
    sortConfig: SortConfig = SortConfig.None,
    sortModes: Set<SortMode> = SortMode.entries.toSet(),
    onSortChange: (SortConfig) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        SortSection(
            modifier = modifier
                .padding(12.dp),
            sortConfig,
            sortModes,
            onSortChange
        )
    }
}

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    sortConfig: SortConfig = SortConfig.None,
    sortModes: Set<SortMode> = SortMode.entries.toSet(),
    onSortChange: (SortConfig) -> Unit
) {
    var width by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .onGloballyPositioned { width = with(density) { it.size.width.toDp() } }
    ) {
        Text(
            text = stringResource(R.string.sortsection_title),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "${stringResource(sortConfig.mode.label)}, ${stringResource(sortConfig.direction.label)}",
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider(
            modifier = Modifier
                .width(width)
                .padding(vertical = 12.dp)
        )
        ModeSection(
            sortMode = sortConfig.mode,
            sortModes = sortModes,
            onModeChange = {
                onSortChange(
                    sortConfig.copy(mode = it)
                )
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .width(width)
                .padding(vertical = 12.dp)
        )
        DirectionSection(
            sortDirection = sortConfig.direction,
            onDirectionChange = {
                onSortChange(
                    sortConfig.copy(direction = it)
                )
            }
        )
    }
}

@Composable
private fun ModeSection(
    modifier: Modifier = Modifier,
    sortMode: SortMode,
    sortModes: Set<SortMode>,
    onModeChange: (SortMode) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        sortModes.forEach { mode ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = sortMode == mode,
                    onClick = {
                        onModeChange(mode)
                    }
                )
                Text(
                    text = stringResource(mode.label),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun DirectionSection(
    modifier: Modifier = Modifier,
    sortDirection: SortDirection,
    onDirectionChange: (SortDirection) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        SortDirection.entries.forEach { direction ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = sortDirection == direction,
                    onClick = {
                        onDirectionChange(direction)
                    }
                )
                Text(
                    text = stringResource(direction.label),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(widthDp = 1000)
@Composable
private fun SortSectionPreview() = AboutMePreview(name = "Sort Section") {
    SortSection(
        modifier = Modifier.width(300.dp),
        sortModes = setOf(SortMode.Length, SortMode.Date, SortMode.Name),
        onSortChange = { }
    )
}

@Preview(widthDp = 1000)
@Composable
private fun SortCardPreview() = AboutMePreview(name = "Sort Card") {
    SortCard(
        modifier = Modifier.width(300.dp),
        sortModes = setOf(SortMode.Length, SortMode.Date, SortMode.Name),
        onSortChange = { }
    )
}