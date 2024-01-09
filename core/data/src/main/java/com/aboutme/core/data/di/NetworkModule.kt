package com.aboutme.core.data.di

import com.aboutme.core.data.AuthService
import com.aboutme.core.data.implementation.NetworkAuthService
import com.aboutme.core.data.implementation.NetworkDailyDataRepository
import com.aboutme.core.data.implementation.NetworkUserRepository
import com.aboutme.core.data.repository.DailyDataRepository
import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.secret.TokenRepository
import com.aboutme.network.source.DailyDataSource
import com.aboutme.network.source.UserNetworkSource
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
    fun provideAuthService(
        networkSource: UserNetworkSource,
        tokenRepository: TokenRepository
    ): AuthService = NetworkAuthService(networkSource, tokenRepository)

    @Provides
    @Singleton
    fun provideDailyDataRepository(
        dailyDataSource: DailyDataSource,
        authService: AuthService
    ): DailyDataRepository = NetworkDailyDataRepository(dailyDataSource, authService)

    @Provides
    @Singleton
    fun provideUserRepository(
        authService: AuthService,
        userNetworkSource: UserNetworkSource
    ): UserRepository = NetworkUserRepository(authService, userNetworkSource)

}