package com.aboutme.core.data.implementation;

import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.database.dao.UserDao
import com.aboutme.core.model.data.NameInfo
import kotlinx.coroutines.flow.first

internal class OfflineUserRepository(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun updateUser(nameInfo: NameInfo) {
        val current = userDao.getAll().first().firstOrNull() ?: return
        userDao.update(current.copy(nameInfo = nameInfo))
    }
}