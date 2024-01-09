package com.aboutme.core.data.implementation;

import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.database.dao.UserDao
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class OfflineUserRepository(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun updateUser(nameInfo: NameInfo) {
        val current = userDao.getAll().first().firstOrNull() ?: return
        userDao.update(current.copy(nameInfo = nameInfo))
    }

    override fun getUser(): Flow<UserData> = userDao
        .getAll()
        .map {
            UserData(
                it.first().nameInfo,
                it.first().email,
                it.first().createdAt,
                it.first().updatedAt
            )
        }
}