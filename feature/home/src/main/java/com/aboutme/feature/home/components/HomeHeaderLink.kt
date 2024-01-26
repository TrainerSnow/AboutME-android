package com.aboutme.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun HomeHeaderLink(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    summary: String? = null,
    icon: ImageVector? = null
) {
    HomeHeaderLink(
        modifier = modifier,
        onClick = onClick,
        title = {
            Text(text = title)
        },
        summary = summary?.let {
            {
                Text(text = summary)
            }
        },
        icon = icon?.let {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = title
                )
            }
        }
    )
}

@Composable
internal fun HomeHeaderLink(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: @Composable () -> Unit,
    summary: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 6.dp
                )
                .widthIn(max = 200.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let { it() }
            Column {
                ProvideTextStyle(MaterialTheme.typography.labelLarge) {
                    title()
                }
                ProvideTextStyle(MaterialTheme.typography.bodySmall) {
                    summary?.let { it() }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeHeaderLinkPreview() {
    HomeHeaderLink(
        onClick = {  },
        title = "Persons",
        summary = "Add or view the persons that you added to your account.",
        icon = Icons.Outlined.Person
    )
}