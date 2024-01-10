package com.aboutme.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aboutme.core.database.entity.daily.DiaryDataEntity
import com.aboutme.core.database.entity.daily.MoodDataEntity
import com.aboutme.core.database.entity.multi.DreamDataWithDreams
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MoodDataDao {

    @Query("SELECT * FROM mood_data WHERE mood_data.date = :date")
    fun getByDate(date: LocalDate): Flow<MoodDataEntity?>

    @Query("SELECT * FROM mood_data")
    fun getAll(): Flow<List<MoodDataEntity>>

    @Query("DELETE FROM mood_data")
    fun deleteAll()

    @Insert
    suspend fun insert(entity: MoodDataEntity)

    @Delete
    suspend fun delete(entity: MoodDataEntity): Int

}