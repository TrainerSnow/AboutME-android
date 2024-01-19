package com.aboutme.core.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aboutme.core.cache.dao.base.SyncableEntityAccessor
import com.aboutme.core.cache.entity.daily.MoodDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MoodDataDao: SyncableEntityAccessor<MoodDataEntity> {

    @Query("SELECT * FROM mood_data WHERE mood_data.date = :date and mood_data.deletedAt IS NULL")
    fun getByDate(date: LocalDate): Flow<MoodDataEntity?>

    @Query("SELECT * FROM mood_data WHERE mood_data.date = :date")
    fun getByDateWithDeleted(date: LocalDate): Flow<MoodDataEntity?>

    @Query("SELECT * FROM mood_data WHERE mood_data.deletedAt IS NULL")
    fun getAll(): Flow<List<MoodDataEntity>>

    @Query("SELECT * FROM mood_data")
    fun getAllWithDeleted(): Flow<List<MoodDataEntity>>

    @Query("DELETE FROM mood_data")
    fun deleteAll()

    @Insert
    override suspend fun insert(entity: MoodDataEntity)

    @Delete
    override suspend fun delete(entity: MoodDataEntity): Int

    @Update
    override suspend fun update(entity: MoodDataEntity)

}