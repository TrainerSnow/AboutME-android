package com.aboutme.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@Composable
fun <T> densityScope(block: Density.() -> T) = with(LocalDensity.current) {
    block()
}