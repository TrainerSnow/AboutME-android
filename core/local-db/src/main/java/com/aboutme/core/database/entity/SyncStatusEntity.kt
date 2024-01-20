package com.aboutme.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "sync_status_entity"
)
data class SyncStatusEntity(

    @PrimaryKey
    val startedAt: Instant,

    val finishedAt: Instant,

    /**
     * 1: Success
     * 2: Error
     * 3: Auth error
     */
    val status: Int

)
