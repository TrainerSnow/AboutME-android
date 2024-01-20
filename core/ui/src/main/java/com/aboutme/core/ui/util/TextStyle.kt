package com.aboutme.core.ui.util;

import androidx.compose.ui.text.TextStyle

fun TextStyle.disabled(enabled: Boolean = true) = if (!enabled) copy(
    color = color.copy(
        alpha = 0.38F
    )
) else this