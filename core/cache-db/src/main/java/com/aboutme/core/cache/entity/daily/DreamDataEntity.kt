package com.aboutme.core.cache.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.cache.entity.base.SyncableEntity
import java.time.Instant
import java.time.LocalDate

@Entity(
    tableName = "dream_data"
)
data class DreamDataEntity(

    @PrimaryKey
    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val deletedAt: Instant? = null

): SyncableEntity
