package com.aboutme.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aboutme.core.database.entity.DreamEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DreamDao {

    @Query("SELECT * FROM dream WHERE dream.deletedOn == null")
    fun getAll(): Flow<List<DreamEntity>>

    @Query("SELECT * FROM dream")
    fun getAllWithDeleted(): Flow<List<DreamEntity>>

    @Query("SELECT * FROM dream WHERE dream.date = :date AND dream.deletedOn == null")
    fun getByDate(date: LocalDate): Flow<List<DreamEntity>>

    @Query("SELECT * FROM dream WHERE dream.date = :date")
    fun getByDateWithDeleted(date: LocalDate): Flow<List<DreamEntity>>

    @Query("SELECT * FROM dream WHERE dream.id = :id")
    fun getById(id: Long): Flow<DreamEntity?>

    @Query("DELETE FROM dream")
    fun deleteAll()

    @Insert
    suspend fun insert(entity: DreamEntity): Long

    @Delete
    suspend fun delete(entity: DreamEntity): Int

}