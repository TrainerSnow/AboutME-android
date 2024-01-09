package com.aboutme.core.data.repository

import com.aboutme.core.data.AuthService
import com.aboutme.core.model.Response
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData

/**
 * Grants access to data related to the user that does not fit into the [AuthService]
 *
 */
interface UserRepository {

    /**
     * Updates the [NameInfo] associated with the current user
     */
    suspend fun updateUser(nameInfo: NameInfo)

}