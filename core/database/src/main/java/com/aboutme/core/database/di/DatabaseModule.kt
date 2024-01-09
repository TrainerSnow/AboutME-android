package com.aboutme.core.database.di

import android.content.Context
import androidx.room.Room
import com.aboutme.core.database.base.AboutMeDatabase
import com.aboutme.core.database.base.AboutMeDatabaseImpl
import com.aboutme.core.database.dao.DiaryDataDao
import com.aboutme.core.database.dao.DreamDao
import com.aboutme.core.database.dao.DreamDataDao
import com.aboutme.core.database.dao.MoodDataDao
import com.aboutme.core.database.dao.SleepDataDao
import com.aboutme.core.database.dao.UserDao
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
    ): AboutMeDatabaseImpl = Room
        .databaseBuilder(
            context = context,
            klass = AboutMeDatabaseImpl::class.java,
            name = "about-me-db"
        )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDiaryDataDao(
        db: AboutMeDatabaseImpl
    ): DiaryDataDao = db.diaryDataDao

    @Provides
    @Singleton
    fun provideDreamDataDao(
        db: AboutMeDatabaseImpl
    ): DreamDataDao = db.dreamDataDao

    @Provides
    @Singleton
    fun provideSleepDataDao(
        db: AboutMeDatabaseImpl
    ): SleepDataDao = db.sleepDataDao

    @Provides
    @Singleton
    fun provideMoodDataDao(
        db: AboutMeDatabaseImpl
    ): MoodDataDao = db.moodDataDao

    @Provides
    @Singleton
    fun provideDreamDao(
        db: AboutMeDatabaseImpl
    ): DreamDao = db.dreamDao

    @Provides
    @Singleton
    fun provideUserDao(
        db: AboutMeDatabaseImpl
    ): UserDao = db.userDao

    @Provides
    @Singleton
    fun provideExposedDatabase(
        db: AboutMeDatabaseImpl
    ): AboutMeDatabase = db

}