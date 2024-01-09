package com.aboutme.network.di

import com.aboutme.core.network.BuildConfig
import com.aboutme.network.implementation.ApolloDailyDataSource
import com.aboutme.network.implementation.ApolloUserNetworkSource
import com.aboutme.network.source.DailyDataSource
import com.aboutme.network.source.UserNetworkSource
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    internal fun provideApolloClient(): ApolloClient = ApolloClient
        .Builder()
        .serverUrl(BuildConfig.GRAPHQL_ROOT_URL)
        .addHttpInterceptor(LoggingInterceptor())
        .build()


    @Singleton
    @Provides
    fun provideUserNetworkSource(
        client: ApolloClient
    ): UserNetworkSource =
        ApolloUserNetworkSource(client)

    @Provides
    @Singleton
    fun provideDailyDataSource(
        client: ApolloClient
    ): DailyDataSource = ApolloDailyDataSource(client)

}