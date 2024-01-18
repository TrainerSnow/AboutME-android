package com.aboutme.network.di

import com.aboutme.core.network.BuildConfig
import com.aboutme.network.implementation.ApolloDreamSource
import com.aboutme.network.implementation.ApolloUserNetworkSource
import com.aboutme.network.implementation.daily.ApolloDiaryDataSource
import com.aboutme.network.implementation.daily.ApolloDreamDataSource
import com.aboutme.network.implementation.daily.ApolloMoodDataSource
import com.aboutme.network.implementation.daily.ApolloSleepDataSource
import com.aboutme.network.source.DreamSource
import com.aboutme.network.source.UserNetworkSource
import com.aboutme.network.source.daily.DiaryDataSource
import com.aboutme.network.source.daily.DreamDataSource
import com.aboutme.network.source.daily.MoodDataSource
import com.aboutme.network.source.daily.SleepDataSource
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
    fun provideDiaryDataSource(
        client: ApolloClient
    ): DiaryDataSource = ApolloDiaryDataSource(client)

    @Provides
    @Singleton
    fun provideSleepDataSource(
        client: ApolloClient
    ): SleepDataSource = ApolloSleepDataSource(client)

    @Provides
    @Singleton
    fun provideMoodDataSource(
        client: ApolloClient
    ): MoodDataSource = ApolloMoodDataSource(client)

    @Provides
    @Singleton
    fun provideDreamDataSource(
        client: ApolloClient
    ): DreamDataSource = ApolloDreamDataSource(client)

    @Provides
    @Singleton
    fun provideDreamSource(
        client: ApolloClient
    ): DreamSource = ApolloDreamSource(client)

}