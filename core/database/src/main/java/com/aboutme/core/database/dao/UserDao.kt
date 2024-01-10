package com.aboutme.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aboutme.core.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE User.email = :email")
    fun getByEmail(email: String): Flow<UserEntity?>

    @Query("DELETE FROM user")
    fun deleteAll()

    @Insert
    suspend fun insert(entity: UserEntity): Long

    @Delete
    suspend fun delete(entity: UserEntity): Int

    @Update
    suspend fun update(entity: UserEntity)

}