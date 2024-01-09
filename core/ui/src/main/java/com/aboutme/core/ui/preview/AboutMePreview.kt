package com.aboutme.core.ui.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aboutme.core.ui.locals.ProvideWindowSizeClass
import com.aboutme.core.ui.util.from

@Composable
fun AboutMePreview(
    name: String,
    windowSizeClass: WindowSizeClass = WindowSizeClass.from(
        WindowWidthSizeClass.Compact,
        WindowHeightSizeClass.Compact
    ),
    content: @Composable () -> Unit
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement
        .spacedBy(32.dp)
) {
    ProvideWindowSizeClass(windowSizeClass) {
        MaterialTheme(
            colorScheme = lightColorScheme()
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "$name - Light",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )
                content()
            }
        }
        MaterialTheme(
            colorScheme = darkColorScheme()
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "$name - Dark",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )
                content()
            }
        }
    }
}