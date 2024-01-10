package com.aboutme.core.secret.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.aboutme.core.secret.TokenRepository
import com.aboutme.core.secret.implementation.ESPTokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecretModule {

    private const val FILENAME = "tokens"
    private const val MASTER_KEY_ALIAS = "com.aboutme.secret.tokens"

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        /*EncryptedSharedPreferences
        .create(
            context,
            FILENAME,
            MasterKey.Builder(context, MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )*/

    @Provides
    @Singleton
    fun provideTokenRepository(
        preferences: SharedPreferences
    ): TokenRepository = ESPTokenRepository(preferences)

}