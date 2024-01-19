package com.aboutme.core.model.preferences

/**
 * Contains the data the user set in the settings
 */
data class UserPreferences(

    val colorMode: ColorMode,

    val colorTheme: ColorTheme,

    val syncPreferences: SyncPreferences

)
