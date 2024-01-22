package com.aboutme.feature.preferences.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aboutme.core.ui.preferences.BasePreference
import com.aboutme.feature.preferences.R
import com.aboutme.feature.preferences.model.PeriodicSyncUnit
import com.aboutme.feature.preferences.model.SyncPeriod
import com.aboutme.feature.preferences.model.localizedName
import com.snow.core.input.data.TextInput
import com.snow.core.input.error.InputError
import com.snow.core.input.component.OutlinedTextField as InputOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SyncPeriodPreference(
    modifier: Modifier = Modifier,
    period: SyncPeriod,
    onPeriodChange: (SyncPeriod) -> Unit,
    enabled: Boolean = true
) {
    val unitNamesMap = PeriodicSyncUnit.entries.associateBy { it.localizedName }

    var showDialog by remember { mutableStateOf(false) }

    var valueInput by remember { mutableStateOf(TextInput(period.value.toString())) }
    var unitInput by remember { mutableStateOf(period.unit) }

    var showDropdown by remember { mutableStateOf(false) }

    BasePreference(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.preference_sync_period)) },
        subtitle = {
            Text(
                text = stringResource(
                    id = R.string.preference_sync_period_subtitle,
                    period.value.toString(),
                    period.unit.localizedName
                )
            )
        },
        onClick = { showDialog = !showDialog },
        enabled = enabled
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val value = valueInput.input.toLongOrNull()
                        if (value == null) {
                            valueInput = valueInput.copy(error = InputError.InvalidIntegerLiteral)
                        } else if (value <= 15 && unitInput == PeriodicSyncUnit.Minute) {
                            valueInput = valueInput.copy(error = InputError.SyncPeriodTooShort)
                        } else {
                            val syncPeriod = SyncPeriod(value, unitInput)
                            showDropdown = false
                            showDialog = false
                            onPeriodChange(syncPeriod)
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.preference_sync_period_confirm)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = !showDialog }
                ) {
                    Text(
                        text = stringResource(R.string.preference_sync_period_dismiss)
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.preference_sync_period)
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.preference_sync_period_summary)
                    )
                    InputOutlinedTextField(
                        value = valueInput,
                        onValueChange = {
                            valueInput = valueInput.copy(input = it)
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.preference_sync_period_value)
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    ExposedDropdownMenuBox(
                        expanded = showDropdown,
                        onExpandedChange = { showDropdown = it }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .menuAnchor(),
                            value = unitInput.localizedName,
                            onValueChange = {
                                unitInput = unitNamesMap[it]!!
                            },
                            label = {
                                Text(
                                    text = stringResource(R.string.preference_sync_period_unit)
                                )
                            },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(showDropdown)
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = showDropdown,
                            onDismissRequest = { showDropdown = false }
                        ) {
                            PeriodicSyncUnit.entries.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it.localizedName) },
                                    onClick = {
                                        unitInput = it
                                        showDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}






















