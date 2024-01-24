package com.aboutme.core.cache.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.cache.entity.base.SyncableEntity
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

@Entity(
    tableName = "dream_data"
)
data class DreamDataEntity(

    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val deletedAt: Instant? = null,

    override val remoteId: UUID?,

    @PrimaryKey(autoGenerate = true)
    override val localId: Long?

): SyncableEntity
