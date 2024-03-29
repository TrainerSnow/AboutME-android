package com.aboutme.core.data.implementation;

import com.aboutme.core.data.repository.UserRepository
import com.aboutme.core.cache.dao.UserDao
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant

internal class OfflineUserRepository(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun updateUser(nameInfo: NameInfo) {
        val current = userDao.getAll().first().firstOrNull() ?: return
        userDao.update(current.copy(nameInfo = nameInfo, updatedAt = Instant.now()))
    }

    override fun getUser(): Flow<UserData?> = userDao
        .getAll()
        .map {
            it.firstOrNull()
        }.map {
            it?.let {
                UserData(it.nameInfo, it.email, it.createdAt, it.updatedAt)
            }
        }
}