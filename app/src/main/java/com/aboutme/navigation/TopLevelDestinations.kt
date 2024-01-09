package com.aboutme.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.aboutme.R

/**
 * The navigation destinations to be shown on the navigation component
 */
internal enum class TopLevelDestinations(

    @StringRes
    val label: Int,

    val icon: ImageVector,

    val route: String

) {

    Home(
        R.string.destination_home,
        Icons.Outlined.Home,
        "home"
    ),

    Calendar(
        R.string.destination_calendar,
        Icons.Outlined.CalendarMonth,
        "calendar"
    ),

    Statistics(
        R.string.destination_statistics,
        Icons.Outlined.AutoGraph,
        "statistics"
    )

}