package com.aboutme.core.data.implementation;

import com.aboutme.core.data.AuthService
import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.model.data.NameInfo
import com.aboutme.network.source.UserNetworkSource

internal class NetworkUserRepository(
    private val authService: AuthService,
    private val userNetworkSource: UserNetworkSource
) : UserRepository {

    override suspend fun deleteUser() = authService.saveAuthTransaction {
        userNetworkSource.deleteUser(it)
    }

    override suspend fun updateUser(nameInfo: NameInfo) = authService.saveAuthTransaction {
        userNetworkSource.updateUser(nameInfo, it)
    }
}