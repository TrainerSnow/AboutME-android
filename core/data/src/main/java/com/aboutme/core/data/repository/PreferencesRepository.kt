package com.aboutme.core.data.repository

import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncPreferences
import com.aboutme.core.model.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow

/**
 * Gives access to the saved preferences
 */
interface PreferencesRepository {

    /**
     * Contains the currently saved user preferences
     */
    val preferences: Flow<UserPreferences>

    suspend fun updateColorTheme(colorTheme: ColorTheme)

    suspend fun updateColorMode(colorMode: ColorMode)

    suspend fun updateSyncPreferences(syncPreferences: SyncPreferences)

}