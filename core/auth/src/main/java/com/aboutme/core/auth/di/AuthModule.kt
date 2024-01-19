package com.aboutme.core.auth.di

import com.aboutme.core.auth.AuthService
import com.aboutme.core.auth.implementation.NetworkAuthService
import com.aboutme.core.cache.base.AboutMeDatabase
import com.aboutme.core.secret.TokenRepository
import com.aboutme.network.source.UserNetworkSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthService(
        networkSource: UserNetworkSource,
        tokenRepository: TokenRepository,
        database: AboutMeDatabase
    ): AuthService = NetworkAuthService(networkSource, tokenRepository, database)

}