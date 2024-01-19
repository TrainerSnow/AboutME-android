package com.aboutme.core.auth

import com.aboutme.core.common.Response
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData

/**
 * Service to handle authentication to the server
 */
interface AuthService {

    /**
     * Creates a new user on the server and saves the authentication tokens
     *
     * @param email The email of the user
     * @param password The password of the user
     * @param nameInfo The naming preferences of the user
     * @return A flow with responses with the created user. In the beginning [Response.Loading].
     */
    suspend fun signUp(email: String, password: String, nameInfo: NameInfo): Response<AuthUser>

    /**
     * Logs the user in.
     *
     * @param email The email of the user
     * @param password The password of the user
     * @return A flow with responses with the logged in user. In the beginning [Response.Loading].
     */
    suspend fun logIn(email: String, password: String): Response<AuthUser>

    /**
     * Logs the user out. Only the current device is affected.
     *
     * @return A flow with responses with the logged out user. In the beginning [Response.Loading].
     */
    suspend fun logOut(): Response<UserData>

    /**
     * Logs the user out. All devices where the user is logged in are affected.
     *
     * @return A flow with responses with the logged out user. In the beginning [Response.Loading].
     */
    suspend fun logOutAll(): Response<UserData>

    /**
     * Refreshes the access token
     */
    suspend fun refresh(): Response<AuthUser>

    /**
     * Deletes the currently logged in user
     */
    suspend fun deleteUser(): Response<UserData>

    /**
     * Runs the network call. In case it errors in an authentication error, this service authenticates the device and the retries it once.
     */
    suspend fun <Data> saveAuthTransaction(
        networkCall: suspend (token: String) -> Response<Data>
    ): Response<Data>

}