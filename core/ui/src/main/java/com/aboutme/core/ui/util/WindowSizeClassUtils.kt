package com.aboutme.core.ui.util

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun WindowSizeClass.Companion.from(
    widthSizeClass: WindowWidthSizeClass,
    heightSizeClass: WindowHeightSizeClass
): WindowSizeClass = calculateFromSize(
    size = DpSize(
        width = when (widthSizeClass) {
            WindowWidthSizeClass.Compact -> 500.dp
            WindowWidthSizeClass.Medium -> 700.dp
            WindowWidthSizeClass.Expanded -> 900.dp
            else -> throw IllegalStateException("Cannot create a WindowSizeClass for widthSizeClass '$widthSizeClass'")
        },
        height = when (heightSizeClass) {
            WindowHeightSizeClass.Compact -> 400.dp
            WindowHeightSizeClass.Medium -> 800.dp
            WindowHeightSizeClass.Expanded -> 100.dp
            else -> throw IllegalStateException("Cannot create a WindowSizeClass for heightSizeClass '$widthSizeClass'")
        }
    )
)