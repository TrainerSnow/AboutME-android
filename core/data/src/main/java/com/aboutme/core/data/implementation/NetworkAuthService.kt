package com.aboutme.core.data.implementation;

import com.aboutme.core.data.AuthService
import com.aboutme.core.database.base.AboutMeDatabase
import com.aboutme.core.model.Response
import com.aboutme.core.model.ResponseError
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.core.secret.TokenRepository
import com.aboutme.core.sync.SyncController
import com.aboutme.network.source.UserNetworkSource

internal class NetworkAuthService(
    private val networkSource: UserNetworkSource,
    private val tokenRepository: TokenRepository,
    private val database: AboutMeDatabase,
    private val syncController: SyncController
) : AuthService {

    override suspend fun signUp(
        email: String,
        password: String,
        nameInfo: NameInfo
    ): Response<AuthUser> {
        val response = networkSource.signUp(
            email, password, nameInfo
        )

        if (response is Response.Success) {
            tokenRepository.setToken(response.data.authData.token)
            tokenRepository.setRefreshToken(response.data.authData.refreshToken)
        }

        syncController.syncNow()
        return response
    }

    override suspend fun logIn(email: String, password: String): Response<AuthUser> {
        val response = networkSource.logIn(email, password)

        if (response is Response.Success) {
            tokenRepository.setToken(response.data.authData.token)
            tokenRepository.setRefreshToken(response.data.authData.refreshToken)
        }

        syncController.syncNow()
        return response
    }


    override suspend fun logOut(): Response<UserData> {
        val refreshToken = tokenRepository.getRefreshToken()
        val token = tokenRepository.getToken()

        //Clear cache, because it is not needed anymore
        database.deleteAllRows()

        return if (refreshToken == null || token == null) {
            Response.Error(setOf(ResponseError.Unknown))
        } else {
            networkSource.logOut(refreshToken, token)
        }
    }

    override suspend fun logOutAll(): Response<UserData> {
        val token = tokenRepository.getToken()

        //Clear cache, because it is not needed anymore
        database.deleteAllRows()

        return if (token == null) {
            Response.Error(setOf(ResponseError.Unknown))
        } else {
            networkSource.logOutAll(token)
        }
    }

    override suspend fun refresh(): Response<AuthUser> {
        val authUser = networkSource.refresh(tokenRepository.getRefreshToken() ?: "")
        if (authUser is Response.Success) {
            tokenRepository.setRefreshToken(authUser.data.authData.refreshToken)
            tokenRepository.setToken(authUser.data.authData.token)
        } else {
            tokenRepository.setRefreshToken(null)
            tokenRepository.setToken(null)
        }

        return authUser
    }

    override suspend fun deleteUser(): Response<UserData> {
        return networkSource.deleteUser(tokenRepository.getToken() ?: "")
    }

    override suspend fun <Data> saveAuthTransaction(networkCall: suspend (token: String) -> Response<Data>): Response<Data> {
        var result = networkCall(tokenRepository.getToken() ?: "")
        if ((result as? Response.Error)?.errors?.contains(ResponseError.NotAuthorized) == true) {
            refresh()
            result = networkCall(tokenRepository.getToken() ?: "")
        }

        return result
    }

}