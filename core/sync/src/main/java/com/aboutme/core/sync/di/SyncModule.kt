package com.aboutme.core.sync.di

import android.content.Context
import androidx.work.WorkManager
import com.aboutme.core.database.dao.SyncStatusDao
import com.aboutme.core.sync.SyncController
import com.aboutme.core.sync.SyncMonitor
import com.aboutme.core.sync.implementation.WorkerSyncController
import com.aboutme.core.sync.implementation.WorkerSyncMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SyncModule {

    @Singleton
    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager = WorkManager.getInstance(context)

    @Singleton
    @Provides
    fun provideSyncController(
        workManager: WorkManager
    ): SyncController = WorkerSyncController(workManager)

    @Singleton
    @Provides
    fun provideSyncMonitor(
        workManager: WorkManager
    ): SyncMonitor = WorkerSyncMonitor(workManager)

}