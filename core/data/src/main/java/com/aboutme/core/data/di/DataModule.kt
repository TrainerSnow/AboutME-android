package com.aboutme.core.data.di

import com.aboutme.core.cache.dao.DiaryDataDao
import com.aboutme.core.cache.dao.DreamDataDao
import com.aboutme.core.cache.dao.MoodDataDao
import com.aboutme.core.cache.dao.SleepDataDao
import com.aboutme.core.cache.dao.UserDao
import com.aboutme.core.data.implementation.DatastorePreferencesRepository
import com.aboutme.core.data.implementation.LocalSyncResultRepository
import com.aboutme.core.data.implementation.OfflineDailyDataRepository
import com.aboutme.core.data.implementation.OfflineUserRepository
import com.aboutme.core.data.repository.DailyDataRepository
import com.aboutme.core.data.repository.PreferencesRepository
import com.aboutme.core.data.repository.SyncResultRepository
import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.database.dao.SyncStatusDao
import com.aboutme.core.datastore.source.UserPreferencesSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

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

    @Provides
    @Singleton
    fun providePreferencesRepository(
        preferencesSource: UserPreferencesSource
    ): PreferencesRepository = DatastorePreferencesRepository(preferencesSource)

    @Provides
    @Singleton
    fun provideSyncResultRepository(
        syncStatusDao: SyncStatusDao
    ): SyncResultRepository = LocalSyncResultRepository(syncStatusDao)

}