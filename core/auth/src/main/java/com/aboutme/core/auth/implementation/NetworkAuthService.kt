package com.aboutme.core.auth.implementation;

import com.aboutme.core.auth.mapping.toDto
import com.aboutme.core.auth.mapping.toModel
import com.aboutme.core.common.Response
import com.aboutme.core.common.ResponseError
import com.aboutme.core.cache.base.AboutMeDatabase
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.core.secret.TokenRepository
import com.aboutme.network.source.UserNetworkSource

internal class NetworkAuthService(
    private val networkSource: UserNetworkSource,
    private val tokenRepository: TokenRepository,
    private val database: AboutMeDatabase,
) : com.aboutme.core.auth.AuthService {

    override suspend fun signUp(
        email: String,
        password: String,
        nameInfo: NameInfo
    ): Response<AuthUser> {
        val response = networkSource.signUp(
            email, password, nameInfo.toDto()
        )

        if (response is Response.Success) {
            tokenRepository.setToken(response.data.authData.token)
            tokenRepository.setRefreshToken(response.data.authData.refreshToken)
        }

        return response.map { it.toModel() }
    }

    override suspend fun logIn(email: String, password: String): Response<AuthUser> {
        val response = networkSource.logIn(email, password)

        if (response is Response.Success) {
            tokenRepository.setToken(response.data.authData.token)
            tokenRepository.setRefreshToken(response.data.authData.refreshToken)
        }

        return response.map { it.toModel() }
    }


    override suspend fun logOut(): Response<UserData> {
        val refreshToken = tokenRepository.getRefreshToken()
        val token = tokenRepository.getToken()

        //Clear cache, because it is not needed anymore
        database.deleteAllRows()

        tokenRepository.setToken(null)
        tokenRepository.setRefreshToken(null)

        return if (refreshToken == null || token == null) {
            Response.Error(setOf(ResponseError.Unknown))
        } else {
            networkSource.logOut(refreshToken, token).map { it.toModel() }
        }
    }

    override suspend fun logOutAll(): Response<UserData> {
        val token = tokenRepository.getToken()

        //Clear cache, because it is not needed anymore
        database.deleteAllRows()

        tokenRepository.setToken(null)
        tokenRepository.setRefreshToken(null)

        return if (token == null) {
            Response.Error(setOf(ResponseError.Unknown))
        } else {
            networkSource.logOutAll(token).map { it.toModel() }
        }
    }

    override suspend fun refresh(): Response<AuthUser> {
        val token = tokenRepository.getRefreshToken()
            ?: return Response.Error(setOf(ResponseError.NotAuthorized))
        val authUser = networkSource.refresh(token)
        if (authUser is Response.Success) {
            tokenRepository.setRefreshToken(authUser.data.authData.refreshToken)
            tokenRepository.setToken(authUser.data.authData.token)
        } else {
            tokenRepository.setRefreshToken(null)
            tokenRepository.setToken(null)
        }

        return authUser.map { it.toModel() }
    }

    override suspend fun deleteUser(): Response<UserData> {
        return saveAuthTransaction {
            networkSource.deleteUser(it).map { it.toModel() }
        }
    }

    override suspend fun <Data> saveAuthTransaction(networkCall: suspend (token: String) -> Response<Data>): Response<Data> {
        var token = tokenRepository.getToken() ?: ""
        var response = networkCall(token)
        if((response as? Response.Error)?.errors?.contains(ResponseError.NotAuthorized) == true) {
            refresh()
            token = tokenRepository.getToken() ?: ""
            response = networkCall(token)
        }

        return response
    }

}