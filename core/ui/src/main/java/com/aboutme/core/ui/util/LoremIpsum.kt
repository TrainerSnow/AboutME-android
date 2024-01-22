package com.aboutme.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

@Composable
fun loremIpsum(words: Int): String {
    require(words > 0)
    return LoremIpsum(words).values.joinToString(" ").replace("\n", " ")
}