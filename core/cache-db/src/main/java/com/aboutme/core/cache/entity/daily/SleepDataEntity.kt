package com.aboutme.core.cache.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.cache.entity.base.SyncableEntity
import java.time.Instant
import java.time.LocalDate

@Entity(
    tableName = "sleep_data"
)
data class SleepDataEntity(

    @PrimaryKey
    val date: LocalDate,

    val hoursSlept: Int,

    val hoursAim: Int?,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val deletedAt: Instant? = null

) : SyncableEntity
