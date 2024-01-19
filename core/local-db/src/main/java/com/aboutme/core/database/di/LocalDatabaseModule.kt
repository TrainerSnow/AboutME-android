package com.aboutme.core.database.di

import android.content.Context
import androidx.room.Room
import com.aboutme.core.database.base.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(
            context,
            LocalDatabase::class.java,
            LocalDatabase.DB_NAME
        )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideSyncStatusDao(
        database: LocalDatabase
    ) = database.syncStatusDao

}