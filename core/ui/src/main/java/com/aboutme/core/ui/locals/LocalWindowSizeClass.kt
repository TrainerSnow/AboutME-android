package com.aboutme.core.ui.locals

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
val LocalWindowSizeClass = compositionLocalOf {
    WindowSizeClass.calculateFromSize(
        DpSize(
            width = 500.dp,
            height = 500.dp
        )
    )
}

@Composable
fun ProvideWindowSizeClass(
    windowSizeClass: WindowSizeClass,
    content: @Composable () -> Unit
) = CompositionLocalProvider(
    LocalWindowSizeClass provides windowSizeClass,
    content
)