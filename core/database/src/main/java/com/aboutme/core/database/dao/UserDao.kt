package com.aboutme.core.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aboutme.core.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): Flow<Set<UserEntity>>

    @Query("SELECT * FROM user WHERE User.email = :email")
    fun getByEmail(email: String): Flow<UserEntity?>

    @Insert
    suspend fun insert(entity: UserEntity): Long

    @Delete
    suspend fun delete(entity: UserEntity): Int

}