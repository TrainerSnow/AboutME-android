package com.aboutme.core.ui.adaptive

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aboutme.core.ui.locals.LocalWindowSizeClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val sizeClass = LocalWindowSizeClass.current
    val heightClass = sizeClass.heightSizeClass
    val widthClass = sizeClass.widthSizeClass

    if (heightClass == WindowHeightSizeClass.Compact || widthClass == WindowWidthSizeClass.Compact) {
        TopAppBar(title, modifier, navigationIcon, actions, windowInsets, colors, scrollBehavior)
    } else if(heightClass == WindowHeightSizeClass.Medium && widthClass >= WindowWidthSizeClass.Medium) {
        MediumTopAppBar(title, modifier, navigationIcon, actions, windowInsets, colors, scrollBehavior)
    } else {
        LargeTopAppBar(title, modifier, navigationIcon, actions, windowInsets, colors, scrollBehavior)
    }
}