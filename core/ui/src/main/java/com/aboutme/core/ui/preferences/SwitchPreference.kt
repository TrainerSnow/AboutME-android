package com.aboutme.core.ui.preferences

import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aboutme.core.ui.util.loremIpsum

@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    switched: Boolean = false,
    onSwitchedChange: (Boolean) -> Unit,
    title: @Composable () -> Unit,
    enabled: Boolean = true,
    subtitle: (@Composable () -> Unit)? = null
) {
    BasePreference(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        enabled = enabled,
        trailingContent = {
            Switch(
                checked = switched,
                onCheckedChange = onSwitchedChange,
                enabled = enabled
            )
        }
    )
}

@Preview
@Composable
private fun CheckboxPreferencePreview() {
    Surface {
        SwitchPreference(
            switched = true,
            onSwitchedChange = {},
            title = {
                Text(
                    text = loremIpsum(3)
                )
            },
            subtitle = {
                Text(
                    text = loremIpsum(20)
                )
            }
        )
    }
}

@Preview
@Composable
private fun CheckboxPreferencePreviewDisabled() {
    Surface {
        SwitchPreference(
            switched = true,
            onSwitchedChange = {},
            title = {
                Text(
                    text = loremIpsum(3)
                )
            },
            subtitle = {
                Text(
                    text = loremIpsum(20)
                )
            },
            enabled = false
        )
    }
}