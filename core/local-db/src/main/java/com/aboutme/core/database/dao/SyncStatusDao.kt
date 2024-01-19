package com.aboutme.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aboutme.core.database.entity.SyncResultData
import com.aboutme.core.database.entity.SyncStatusEntity
import com.aboutme.core.database.entity.multi.SyncStatusWithResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncStatusDao {

    @Query("SELECT * FROM sync_status_entity")
    fun getAll(): Flow<List<SyncStatusEntity>>

    @Query("SELECT * FROM sync_status_entity")
    @Transaction
    fun getAllWithResult(): Flow<List<SyncStatusWithResult>>

    @Insert
    suspend fun insert(entity: SyncStatusEntity)

    @Insert
    suspend fun insert(entity: SyncResultData)

    @Update
    suspend fun update(entity: SyncStatusEntity)

}