package com.aboutme.core.cache.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.cache.entity.base.SyncableEntity
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

@Entity(
    tableName = "diary_data"
)
data class DiaryDataEntity(

    val date: LocalDate,

    val content: String,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val deletedAt: Instant? = null,

    override val remoteId: UUID? = null,

    @PrimaryKey(autoGenerate = true)
    override val localId: Long?

) : SyncableEntity
