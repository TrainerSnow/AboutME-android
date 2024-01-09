package com.aboutme.core.secret.implementation;

import android.content.SharedPreferences
import androidx.core.content.edit
import com.aboutme.core.secret.TokenRepository

internal class ESPTokenRepository(

    private val preferences: SharedPreferences

) : TokenRepository {

    override fun setToken(token: String?) {
        preferences.edit {
            putString(KEY_TOKEN, token)
        }
    }

    override fun setRefreshToken(refreshToken: String?) {
        preferences.edit {
            putString(KEY_REFRESH, refreshToken)
        }
    }

    override fun getToken() = preferences
        .getString(KEY_TOKEN, null)

    override fun getRefreshToken() = preferences
        .getString(KEY_REFRESH, null)

    companion object {

        private const val KEY_TOKEN = "jwt_token"

        private const val KEY_REFRESH = "refresh_token"

    }

}