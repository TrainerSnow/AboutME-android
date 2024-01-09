package com.aboutme.core.database.di

import android.content.Context
import androidx.room.Room
import com.aboutme.core.database.base.AboutMeDatabase
import com.aboutme.core.database.dao.DiaryDataDao
import com.aboutme.core.database.dao.DreamDao
import com.aboutme.core.database.dao.DreamDataDao
import com.aboutme.core.database.dao.MoodDataDao
import com.aboutme.core.database.dao.SleepDataDao
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

    @Provides
    @Singleton
    fun provideDiaryDataDao(
        db: AboutMeDatabase
    ): DiaryDataDao = db.diaryDataDao

    @Provides
    @Singleton
    fun provideDreamDataDao(
        db: AboutMeDatabase
    ): DreamDataDao = db.dreamDataDao

    @Provides
    @Singleton
    fun provideSleepDataDao(
        db: AboutMeDatabase
    ): SleepDataDao = db.sleepDataDao

    @Provides
    @Singleton
    fun provideMoodDataDao(
        db: AboutMeDatabase
    ): MoodDataDao = db.moodDataDao

    @Provides
    @Singleton
    fun provideDreamDao(
        db: AboutMeDatabase
    ): DreamDao = db.dreamDao

}