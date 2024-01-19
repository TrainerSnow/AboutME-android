package com.aboutme.core.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aboutme.core.cache.dao.base.SyncableEntityAccessor
import com.aboutme.core.cache.entity.DreamEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DreamDao: SyncableEntityAccessor<DreamEntity> {

    @Query("SELECT * FROM dream WHERE dream.deletedAt IS NULL")
    fun getAll(): Flow<List<DreamEntity>>

    @Query("SELECT * FROM dream")
    fun getAllWithDeleted(): Flow<List<DreamEntity>>

    @Query("SELECT * FROM dream WHERE dream.date = :date AND dream.deletedAt IS NULL")
    fun getByDate(date: LocalDate): Flow<List<DreamEntity>>

    @Query("SELECT * FROM dream WHERE dream.date = :date")
    fun getByDateWithDeleted(date: LocalDate): Flow<List<DreamEntity>>

    @Query("SELECT * FROM dream WHERE dream.id = :id")
    fun getById(id: Long): Flow<DreamEntity?>

    @Query("DELETE FROM dream")
    fun deleteAll()

    @Insert
    override suspend fun insert(entity: DreamEntity)

    @Delete
    override suspend fun delete(entity: DreamEntity): Int

    @Update
    override suspend fun update(entity: DreamEntity)

}