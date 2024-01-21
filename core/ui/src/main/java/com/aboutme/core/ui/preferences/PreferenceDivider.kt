package com.aboutme.core.ui.preferences

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PreferenceDivider(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(PreferenceDividerDefaults.padding)
    ) {
        ProvideTextStyle(
            value = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.primary.copy(alpha = if(enabled) 1F else 0.38F)
            ),
            content = label
        )
    }
}

@Preview
@Composable
private fun PreferenceDividerPreview() {
    Surface {
        PreferenceDivider {
            Text(
                text = "Settings category"
            )
        }
    }
}

object PreferenceDividerDefaults {

    val padding = PaddingValues(
        start = 16.dp,
        top = 16.dp,
        bottom = 4.dp
    )

}