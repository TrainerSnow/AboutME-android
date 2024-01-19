package com.aboutme.network.source

import com.aboutme.network.dto.AuthUserDto
import com.aboutme.network.dto.NameInfoDto
import com.aboutme.network.dto.UserDto
import com.aboutme.core.common.Response
import com.aboutme.network.dto.update.UpdateUserDto
import com.aboutme.network.source.base.SyncableDtoAccessor
import com.aboutme.type.AuthUser

/**
 * Network source for getting data about the user
 */
interface UserNetworkSource: SyncableDtoAccessor<UserDto, UpdateUserDto, String> {

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
    suspend fun signUp(email: String, password: String, nameInfo: NameInfoDto): Response<AuthUserDto>

    /**
     * Logs in to a user on the server
     *
     * @param email The email
     * @param password The password
     * @return The Response of the data.
     */
    suspend fun logIn(email: String, password: String): Response<AuthUserDto>

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
    suspend fun logOut(refreshToken: String, token: String): Response<UserDto>

    /**
     * Logs out the user on all devices
     *
     * @param token The access token
     * @return The Response of the data.
     */
    suspend fun logOutAll(token: String): Response<UserDto>

    /**
     * Creates a new access-token for the user
     *
     * @param refreshToken The refresh-token
     * @return The Response of the data.
     */
    suspend fun refresh(refreshToken: String): Response<AuthUserDto>

    /**
     * Deletes the logged in user
     *
     * @return The Response of the deleted user
     */
    suspend fun deleteUser(token: String): Response<UserDto>

}