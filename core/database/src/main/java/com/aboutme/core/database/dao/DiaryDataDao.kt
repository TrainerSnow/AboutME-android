package com.aboutme.core.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aboutme.core.database.entity.daily.DiaryDataEntity
import com.aboutme.core.database.entity.multi.DreamDataWithDreams
import com.aboutme.core.model.daily.data.DreamData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DiaryDataDao {

    @Query("SELECT * FROM diary_data WHERE diary_data.date = :date")
    fun getByDate(date: LocalDate): Flow<DiaryDataEntity?>

    @Query("SELECT * FROM diary_data")
    fun getAll(): Flow<Set<DiaryDataEntity>>

    @Insert
    suspend fun insert(entity: DiaryDataEntity)

    @Delete
    suspend fun delete(entity: DiaryDataEntity): Int

}