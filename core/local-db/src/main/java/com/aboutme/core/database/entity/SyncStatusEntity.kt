package com.aboutme.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "sync_status_entity"
)
data class SyncStatusEntity(

    @PrimaryKey
    val finishedAt: Instant?,

    /**
     * 1: Success
     * 2: Error
     */
    val status: Int

)
