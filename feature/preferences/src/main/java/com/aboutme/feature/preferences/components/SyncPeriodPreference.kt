package com.aboutme.feature.preferences.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboutme.core.ui.preferences.BasePreference
import com.aboutme.core.ui.util.ProvideDisabledContentColor
import com.aboutme.feature.preferences.R
import com.aboutme.feature.preferences.model.PeriodicSyncUnit
import com.aboutme.feature.preferences.model.SyncPeriod
import com.aboutme.feature.preferences.model.localizedName

@Composable
internal fun SyncPeriodPreference(
    modifier: Modifier = Modifier,
    period: SyncPeriod,
    enabled: Boolean = true,
    onPeriodChange: (SyncPeriod) -> Unit
) {
    var valueString by remember { mutableStateOf(period.value.toString()) }
    var periodUnit by remember { mutableStateOf(PeriodicSyncUnit.Hour) }
    var showPopup by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
    ) {
        BasePreference(
            title = {
                Text(
                    text = stringResource(R.string.preference_sync_period)
                )
            },
            subtitle = {
                Text(
                    text = stringResource(R.string.preference_sync_period_subtitle)
                )
            },
            enabled = enabled
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideDisabledContentColor(enabled) {
                Text(
                    text = stringResource(R.string.preference_sync_period_1),
                    style = MaterialTheme.typography.titleSmall
                )
                TextField(
                    modifier = Modifier
                        .width(50.dp),
                    value = valueString,
                    onValueChange = {
                        val value = it.toLongOrNull()
                        if (value == null) {
                            errorMsg = R.string.preference_sync_period_error_format
                            valueString = it
                        } else if (value <= 15 && periodUnit == PeriodicSyncUnit.Minute) {
                            errorMsg = R.string.preference_sync_period_error_small
                            valueString = it
                        } else {
                            val newPeriod = SyncPeriod(value, periodUnit)
                            onPeriodChange(newPeriod)
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    textStyle = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.Center)
                        ,
                    enabled = enabled
                )
                Text(
                    text = stringResource(R.string.preference_sync_period_2),
                    style = MaterialTheme.typography.titleSmall
                )
                Box {
                    Row(
                        modifier = Modifier
                            .clickable(
                                enabled = enabled,
                                onClick = {
                                    showPopup = !showPopup
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = periodUnit.localizedName,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDown,
                            contentDescription = stringResource(R.string.preference_sync_period_expand_unit)
                        )
                    }

                    DropdownMenu(
                        expanded = showPopup && enabled,
                        onDismissRequest = { showPopup = false }
                    ) {
                        PeriodicSyncUnit.entries.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(text = unit.localizedName) },
                                onClick = {
                                    periodUnit = unit
                                    showPopup = false
                                }
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(R.string.preference_sync_period_3),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun SyncPeriodPreferencePreview() {
    Surface {
        SyncPeriodPreference(period = SyncPeriod(5, PeriodicSyncUnit.Hour)) {

        }
    }
}






















