package com.aboutme.core.model.data

/**
 * A user together with the auth data belonging to it
 */
data class AuthUser(

    /**
     * The user
     */
    val user: UserData,

    /**
     * The auth data
     */
    val authData: AuthData

)
