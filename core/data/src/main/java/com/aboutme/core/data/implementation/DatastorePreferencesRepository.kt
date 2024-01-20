package com.aboutme.core.data.implementation

import com.aboutme.core.data.repository.PreferencesRepository
import com.aboutme.core.datastore.source.UserPreferencesSource
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncPreferences
import com.aboutme.core.model.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow

internal class DatastorePreferencesRepository(
    private val prefSource: UserPreferencesSource
) : PreferencesRepository {

    override val preferences: Flow<UserPreferences> = prefSource.preferences

    override suspend fun updateColorTheme(colorTheme: ColorTheme) {
        prefSource.updateColorTheme(colorTheme)
    }

    override suspend fun updateColorMode(colorMode: ColorMode) {
        prefSource.updateColorMode(colorMode)
    }

    override suspend fun updateSyncPreferences(syncPreferences: SyncPreferences) {
        prefSource.updateSyncPreferences(syncPreferences)
    }

}