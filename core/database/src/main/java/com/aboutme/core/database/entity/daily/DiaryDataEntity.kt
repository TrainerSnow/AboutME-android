package com.aboutme.core.database.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.database.entity.base.SyncableEntity
import java.time.Instant
import java.time.LocalDate

@Entity(
    tableName = "diary_data"
)
data class DiaryDataEntity(

    @PrimaryKey
    val date: LocalDate,

    val content: String,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val deletedAt: Instant? = null

): SyncableEntity
