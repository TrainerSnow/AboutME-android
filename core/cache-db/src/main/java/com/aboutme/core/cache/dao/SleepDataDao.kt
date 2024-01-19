package com.aboutme.core.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aboutme.core.cache.dao.base.SyncableEntityAccessor
import com.aboutme.core.cache.entity.daily.SleepDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface SleepDataDao : SyncableEntityAccessor<SleepDataEntity> {

    @Query("SELECT * FROM sleep_data WHERE sleep_data.date = :date AND sleep_data.deletedAt IS NULL")
    fun getByDate(date: LocalDate): Flow<SleepDataEntity?>

    @Query("SELECT * FROM sleep_data WHERE sleep_data.date = :date")
    fun getByDateWithDeleted(date: LocalDate): Flow<SleepDataEntity?>

    @Query("SELECT * FROM sleep_data WHERE sleep_data.deletedAt IS NULL")
    fun getAll(): Flow<List<SleepDataEntity>>

    @Query("SELECT * FROM sleep_data")
    fun getAllWithDeleted(): Flow<List<SleepDataEntity>>

    @Query("DELETE FROM sleep_data")
    fun deleteAll()

    @Insert
    override suspend fun insert(entity: SleepDataEntity)

    @Delete
    override suspend fun delete(entity: SleepDataEntity): Int

    @Update
    override suspend fun update(entity: SleepDataEntity)

}