package com.aboutme.core.secret

/**
 * Repository that grants access to the securely encrypted tokens stored on the device of a user
 */
interface TokenRepository {

    /**
     * Sets or updates the current JWT access token
     *
     * @param token The new JWT token
     */
    fun setToken(token: String?)

    /**
     * Sets or updates the current refresh token
     *
     * @param refreshToken The new refresh token
     */
    fun setRefreshToken(refreshToken: String?)

    /**
     * Gets the current JWT access token
     *
     * @return The current JWT access token
     */
    fun getToken(): String?

    /**
     * Gets the current refresh token
     *
     * @return The current refresh token
     */
    fun getRefreshToken(): String?

}