package com.aboutme.core.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aboutme.core.cache.dao.base.SyncableEntityAccessor
import com.aboutme.core.cache.entity.daily.DiaryDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DiaryDataDao: SyncableEntityAccessor<DiaryDataEntity> {

    @Query("SELECT * FROM diary_data WHERE diary_data.date = :date AND diary_data.deletedAt IS NULL")
    fun getByDate(date: LocalDate): Flow<DiaryDataEntity?>

    @Query("SELECT * FROM diary_data WHERE diary_data.date = :date")
    fun getByDateWithDeleted(date: LocalDate): Flow<DiaryDataEntity?>

    @Query("SELECT * FROM diary_data WHERE diary_data.deletedAt IS NULL")
    fun getAll(): Flow<List<DiaryDataEntity>>

    @Query("SELECT * FROM diary_data")
    fun getAllWithDeleted(): Flow<List<DiaryDataEntity>>

    @Query("DELETE FROM diary_data")
    fun deleteAll()

    @Insert
    override suspend fun insert(entity: DiaryDataEntity)

    @Delete
    override suspend fun delete(entity: DiaryDataEntity): Int

    @Update
    override suspend fun update(entity: DiaryDataEntity)

}