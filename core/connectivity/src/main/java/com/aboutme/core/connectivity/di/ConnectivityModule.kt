package com.aboutme.core.connectivity.di

import android.content.Context
import android.net.ConnectivityManager
import com.aboutme.core.connectivity.ConnectivityMonitor
import com.aboutme.core.connectivity.implementation.AndroidConnectivityMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {

    @Provides
    @Singleton
    fun provideConnectivityMonitor(
        @ApplicationContext context: Context
    ): ConnectivityMonitor = AndroidConnectivityMonitor(
        conMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

}