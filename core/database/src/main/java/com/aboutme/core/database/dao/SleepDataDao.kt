package com.aboutme.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aboutme.core.database.entity.daily.SleepDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface SleepDataDao {

    @Query("SELECT * FROM sleep_data WHERE sleep_data.date = :date AND sleep_data.deletedOn == null")
    fun getByDate(date: LocalDate): Flow<SleepDataEntity?>

    @Query("SELECT * FROM sleep_data WHERE sleep_data.date = :date")
    fun getByDateWithDeleted(date: LocalDate): Flow<SleepDataEntity?>

    @Query("SELECT * FROM sleep_data WHERE sleep_data.deletedOn == null")
    fun getAll(): Flow<List<SleepDataEntity>>

    @Query("SELECT * FROM sleep_data")
    fun getAllWithDeleted(): Flow<List<SleepDataEntity>>

    @Query("DELETE FROM sleep_data")
    fun deleteAll()

    @Insert
    suspend fun insert(entity: SleepDataEntity)

    @Delete
    suspend fun delete(entity: SleepDataEntity): Int

}