package com.aboutme.feature.auth.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aboutme.core.ui.locals.LocalWindowSizeClass

@Composable
internal fun AuthScreen(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(tablet: Boolean) -> Unit
) {
    val sizeClass = LocalWindowSizeClass.current
    val tablet =
        sizeClass.widthSizeClass > WindowWidthSizeClass.Compact && sizeClass.heightSizeClass > WindowHeightSizeClass.Compact

    if (tablet) {
        AuthScreenTablet(modifier) { content(true) }
    } else {
        AuthScreenMobile(modifier) { content(false) }
    }
}

@Composable
private fun AuthScreenMobile(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        content = content
    )
}

@Composable
private fun AuthScreenTablet(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .height(750.dp)
                    .width(400.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}