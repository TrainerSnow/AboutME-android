package com.aboutme.core.datastore.implementation;

import androidx.datastore.core.DataStore
import com.aboutme.core.datastore.mapping.toModel
import com.aboutme.core.datastore.mapping.toProto
import com.aboutme.core.datastore.proto.UserPreferencesProto
import com.aboutme.core.datastore.source.UserPreferencesSource
import com.aboutme.core.model.preferences.ColorMode
import com.aboutme.core.model.preferences.ColorTheme
import com.aboutme.core.model.preferences.SyncPreferences
import com.aboutme.core.model.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProtoUserPreferencesSource(
    private val dataStore: DataStore<UserPreferencesProto>
) : UserPreferencesSource {

    override val preferences: Flow<UserPreferences>
        get() = dataStore
            .data
            .map { it.toModel() }

    override suspend fun updateColorTheme(colorTheme: ColorTheme) {
        dataStore
            .updateData {
                it
                    .toBuilder()
                    .setColorTheme(colorTheme.toProto())
                    .build()
            }
    }

    override suspend fun updateColorMode(colorMode: ColorMode) {
        dataStore
            .updateData {
                it
                    .toBuilder()
                    .setColorMode(colorMode.toProto())
                    .build()
            }
    }

    override suspend fun updateSyncPreferences(syncPreferences: SyncPreferences) {
        dataStore
            .updateData {
                it
                    .toBuilder()
                    .setSyncPreferences(syncPreferences.toProto())
                    .build()
            }
    }


}