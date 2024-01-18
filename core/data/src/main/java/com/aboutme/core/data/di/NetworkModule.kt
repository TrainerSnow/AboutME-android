package com.aboutme.core.data.di

import com.aboutme.core.auth.AuthService
import com.aboutme.core.data.implementation.OfflineDailyDataRepository
import com.aboutme.core.data.implementation.OfflineUserRepository
import com.aboutme.core.data.repository.DailyDataRepository
import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.database.base.AboutMeDatabase
import com.aboutme.core.database.dao.DiaryDataDao
import com.aboutme.core.database.dao.DreamDataDao
import com.aboutme.core.database.dao.MoodDataDao
import com.aboutme.core.database.dao.SleepDataDao
import com.aboutme.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDailyDataRepository(
        diaryDataDao: DiaryDataDao,
        sleepDataDao: SleepDataDao,
        dreamDataDao: DreamDataDao,
        moodDataDao: MoodDataDao
    ): DailyDataRepository =
        OfflineDailyDataRepository(diaryDataDao, sleepDataDao, dreamDataDao, moodDataDao)

    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao
    ): UserRepository = OfflineUserRepository(userDao)

}