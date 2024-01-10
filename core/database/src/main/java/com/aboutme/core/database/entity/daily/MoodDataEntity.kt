package com.aboutme.core.database.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.database.entity.base.SyncableEntity
import java.time.Instant
import java.time.LocalDate

@Entity(
    tableName = "mood_data"
)
data class MoodDataEntity(

    @PrimaryKey
    val date: LocalDate,

    val mood: Float?,

    val moodMorning: Float?,

    val moodNoon: Float?,

    val moodEvening: Float?,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val deletedAt: Instant? = null

): SyncableEntity
