package com.aboutme.core.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.cache.entity.base.SyncableEntity
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

@Entity(
    tableName = "dream"
)
data class DreamEntity(

    //Nullable in order to let it autogenerate. When it has been inserted, this should not be null anymore
    @PrimaryKey(autoGenerate = true)
    val localId: Long? = null,

    //Null when the entity has not yet been synced to the remote storage
    val remoteId: UUID? = null,

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
