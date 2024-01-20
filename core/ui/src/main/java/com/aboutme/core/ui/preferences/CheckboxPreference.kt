package com.aboutme.core.ui.preferences

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aboutme.core.ui.util.loremIpsum

@Composable
fun CheckboxPreference(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
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
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled
            )
        }
    )
}

@Preview
@Composable
private fun CheckboxPreferencePreview() {
    Surface {
        CheckboxPreference(
            checked = true,
            onCheckedChange = {},
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
        CheckboxPreference(
            checked = true,
            onCheckedChange = {},
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