package com.aboutme.core.database.di

import android.content.Context
import androidx.room.Room
import com.aboutme.core.database.base.AboutMeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AboutMeDatabase = Room
        .databaseBuilder(
            context = context,
            klass = AboutMeDatabase::class.java,
            name = "about-me-db"
        )
        .fallbackToDestructiveMigration()
        .build()

}