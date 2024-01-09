package com.aboutme.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aboutme.profile.R
import com.snow.core.input.component.OutlinedTextField
import com.snow.core.input.data.TextInput
import com.snow.core.input.error.InputError

@Composable
internal fun DeleteProfileDialog(
    expectedEmail: String,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    var emailInput by remember { mutableStateOf(TextInput()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (emailInput.input == expectedEmail) {
                        onDelete()
                    } else {
                        emailInput = emailInput.copy(error = InputError.EmailConfirmationNotMatch)
                    }
                }
            ) {
                Text(
                    text = stringResource(android.R.string.ok)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(android.R.string.cancel)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.delete_account_dialog_title)
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.delete_account_dialog_text)
                )
                OutlinedTextField(
                    value = emailInput,
                    onValueChange = {
                        emailInput = emailInput.copy(
                            input = it,
                            error = if (emailInput.isError() && it == expectedEmail) null else emailInput.error
                        )
                    }
                )
            }
        }
    )
}