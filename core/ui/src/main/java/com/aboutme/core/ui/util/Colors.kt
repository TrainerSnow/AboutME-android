package com.aboutme.core.ui.util

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp

@Composable
fun CardDefaults.errorCardColors() = CardDefaults
    .cardColors(
        containerColor = MaterialTheme.colorScheme.errorContainer,
        contentColor = contentColorFor(MaterialTheme.colorScheme.errorContainer),
        disabledContainerColor = MaterialTheme.colorScheme.surface
            .copy(alpha = 0.38F)
            .compositeOver(
                MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp)
            ),
        disabledContentColor = contentColorFor(MaterialTheme.colorScheme.errorContainer).copy(alpha = 0.38F)
    )