package com.aboutme.core.model.data

/**
 * The data about the current user
 */
data class UserData(

    /**
     * The naming preferences of the user
     */
    val nameInfo: NameInfo,

    /**
     * The email of the user
     */
    val email: String

)
