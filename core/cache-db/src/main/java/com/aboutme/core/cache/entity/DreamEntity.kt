package com.aboutme.core.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.cache.entity.base.SyncableEntity
import java.time.Instant
import java.time.LocalDate

@Entity(
    tableName = "dream"
)
data class DreamEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    //Foreign key to [DreamData]
    val date: LocalDate,

    val content: String,

    val annotation: String? = null,

    val mood: Float? = null,

    val clearness: Float? = null,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val deletedAt: Instant? = null
) : SyncableEntity
