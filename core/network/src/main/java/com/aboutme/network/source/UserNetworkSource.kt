package com.aboutme.network.source

import com.aboutme.core.model.Response
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData

/**
 * Network source for getting data about the user
 */
interface UserNetworkSource {

    //
    // Unauthenticated
    //

    /**
     * Creates a user on the server
     *
     * @param email The email
     * @param password The password
     * @param nameInfo The [NameInfo]
     * @return The Response of the data.
     */
    suspend fun signUp(email: String, password: String, nameInfo: NameInfo): Response<AuthUser>

    /**
     * Logs in to a user on the server
     *
     * @param email The email
     * @param password The password
     * @return The Response of the data.
     */
    suspend fun logIn(email: String, password: String): Response<AuthUser>

    //
    // Authenticated
    //

    /**
     * Logs out the device with the specified refresh token
     *
     * @param refreshToken The refresh-token to log out
     * @param token The access token
     * @return The Response of the data.
     */
    suspend fun logOut(refreshToken: String, token: String): Response<UserData>

    /**
     * Logs out the user on all devices
     *
     * @param token The access token
     * @return The Response of the data.
     */
    suspend fun logOutAll(token: String): Response<UserData>

    /**
     * Creates a new access-token for the user
     *
     * @param refreshToken The refresh-token
     * @return The Response of the data.
     */
    suspend fun refresh(refreshToken: String): Response<AuthUser>

    /**
     * Deletes the logged in user
     *
     * @return The Response of the deleted user
     */
    suspend fun deleteUser(token: String): Response<UserData>

    /**
     * Updates the nameInfo of the logged in user
     *
     * @return The Response of the updated user
     */
    suspend fun updateUser(nameInfo: NameInfo, token: String): Response<UserData>

}