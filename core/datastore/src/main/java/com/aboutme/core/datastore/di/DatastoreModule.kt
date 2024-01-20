package com.aboutme.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.aboutme.core.datastore.implementation.ProtoUserPreferencesSource
import com.aboutme.core.datastore.proto.UserPreferencesProto
import com.aboutme.core.datastore.serializer.UserPreferencesSerializer
import com.aboutme.core.datastore.source.UserPreferencesSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {


    @Provides
    @Singleton
    fun provideDatastore(
        @ApplicationContext context: Context
    ): DataStore<UserPreferencesProto> = DataStoreFactory
        .create(
            serializer = UserPreferencesSerializer
        ) {
            File(context.applicationContext.filesDir, "datastore/user_preferences.pb")
        }

    @Provides
    @Singleton
    fun providePreferencesSource(
        dataStore: DataStore<UserPreferencesProto>
    ): UserPreferencesSource = ProtoUserPreferencesSource(dataStore)

}