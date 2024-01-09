package com.aboutme.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.ui.R

@Composable
fun NameInfoDialog(
    nameInfo: NameInfo,
    onNameChange: (NameInfo) -> Unit
) {
    var firstName by remember { mutableStateOf(nameInfo.firstName) }
    var middleName by remember { mutableStateOf(nameInfo.middleName ?: "") }
    var lastName by remember { mutableStateOf(nameInfo.lastName ?: "") }
    var title by remember { mutableStateOf(nameInfo.title ?: "") }

    AlertDialog(
        onDismissRequest = { onNameChange(nameInfo) },
        confirmButton = {
            TextButton(
                onClick = {
                    onNameChange(
                        NameInfo(
                            firstName,
                            middleName.ifBlank { null },
                            lastName.ifBlank { null },
                            title.ifBlank { null })
                    )
                }
            ) {
                Text(
                    text = stringResource(android.R.string.ok)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onNameChange(nameInfo)
                }
            ) {
                Text(
                    text = stringResource(android.R.string.cancel)
                )
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement
                    .spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = {
                        Text(
                            text = stringResource(R.string.first_name)
                        )
                    }
                )
                OutlinedTextField(
                    value = middleName,
                    onValueChange = { middleName = it },
                    label = {
                        Text(
                            text = stringResource(R.string.middle_name)
                        )
                    }
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = {
                        Text(
                            text = stringResource(R.string.last_name)
                        )
                    }
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = {
                        Text(
                            text = stringResource(R.string.title)
                        )
                    }
                )
            }
        }
    )
}