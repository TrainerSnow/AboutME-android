package com.aboutme.core.datastore.source

import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncPreferences
import com.aboutme.core.model.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow

/**
 * An accessor to the saved user preferences
 */
interface UserPreferencesSource {

    /**
     * A flow containing the values of the saved user preferences
     */
    val preferences: Flow<UserPreferences>

    /**
     * Updates the saved color theme
     */
    suspend fun updateColorTheme(colorTheme: ColorTheme)

    /**
     * Updates the saved color mode
     */
    suspend fun updateColorMode(colorMode: ColorMode)

    /**
     * Updates the saved sync preferences
     */
    suspend fun updateSyncPreferences(syncPreferences: SyncPreferences)

}