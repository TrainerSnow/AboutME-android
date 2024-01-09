package com.aboutme.core.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aboutme.core.database.entity.daily.DiaryDataEntity
import com.aboutme.core.database.entity.daily.DreamDataEntity
import com.aboutme.core.database.entity.multi.DreamDataWithDreams
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DreamDataDao {

    @Query("SELECT * FROM dream_data WHERE dream_data.date = :date")
    fun getWithDreamsByDate(date: LocalDate): Flow<DreamDataWithDreams?>

    @Query("SELECT * FROM dream_data")
    fun getAllWithDreams(): Flow<Set<DreamDataWithDreams>>

    @Insert
    suspend fun insert(entity: DreamDataEntity)

    @Delete
    suspend fun delete(entity: DreamDataEntity): Int

}