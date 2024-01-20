package com.aboutme.core.ui.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboutme.core.ui.util.disabled
import com.aboutme.core.ui.util.loremIpsum

@Composable
fun BasePreference(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clickable(
                enabled = onClick != null && enabled,
                onClick = onClick ?: {},
                role = Role.Tab
            )
            .padding(BasePreferenceDefaults.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BasePreferenceDefaults.trailingSpacing)
    ) {
        Column(
            modifier = Modifier
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(BasePreferenceDefaults.titleSubtitleSpacing)
        ) {
            ProvideTextStyle(
                value = MaterialTheme.typography.titleMedium.disabled(enabled),
                content = title
            )
            subtitle?.let {
                ProvideTextStyle(
                    value = MaterialTheme.typography.bodySmall.disabled(enabled),
                    content = subtitle
                )
            }
        }
        trailingContent?.let {
            it()
        }
    }
}

@Preview
@Composable
private fun BasePreferencePreview() {
    Surface {
        BasePreference(
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
private fun BasePreferencePreviewDisabled() {
    Surface {
        BasePreference(
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

object BasePreferenceDefaults {

    val padding = PaddingValues(
        horizontal = 12.dp,
        vertical = 12.dp
    )

    val trailingSpacing = 8.dp

    val titleSubtitleSpacing = 4.dp

}