package com.aboutme.core.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aboutme.core.cache.dao.base.SyncableEntityAccessor
import com.aboutme.core.cache.entity.daily.DreamDataEntity
import com.aboutme.core.cache.entity.multi.DreamDataWithDreams
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DreamDataDao : SyncableEntityAccessor<DreamDataEntity> {

    @Query("SELECT * FROM dream_data WHERE dream_data.date = :date AND dream_data.deletedAt IS NULL")
    fun getWithDreamsByDate(date: LocalDate): Flow<DreamDataWithDreams?>

    @Query("SELECT * FROM dream_data WHERE dream_data.date = :date")
    fun getWithDreamsByDateWithDeleted(date: LocalDate): Flow<DreamDataWithDreams?>

    @Query("SELECT * FROM dream_data WHERE dream_data.deletedAt IS NULL")
    fun getAllWithDreams(): Flow<List<DreamDataWithDreams>>

    @Query("SELECT * FROM dream_data")
    fun getAllWithDreamsWithDeleted(): Flow<List<DreamDataWithDreams>>

    @Query("DELETE FROM dream_data")
    fun deleteAll()

    @Insert
    override suspend fun insert(entity: DreamDataEntity)

    @Delete
    override suspend fun delete(entity: DreamDataEntity): Int

    @Update
    override suspend fun update(entity: DreamDataEntity)

}