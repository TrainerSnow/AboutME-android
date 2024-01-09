package com.aboutme.core.model.data

/**
 * Bundles the two authentication tokens in one object
 *
 */
data class AuthData(

    /**
     * The refresh token, UUID
     */
    val refreshToken: String,

    /**
     * The access token, JWT
     */
    val token: String

)
