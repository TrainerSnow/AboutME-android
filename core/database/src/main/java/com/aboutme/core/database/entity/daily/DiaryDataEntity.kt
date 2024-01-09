package com.aboutme.core.database.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate

@Entity(
    tableName = "diary_data"
)
data class DiaryDataEntity(

    @PrimaryKey
    val date: LocalDate,

    val content: String,

    val createdAt: Instant,

    val updatedAt: Instant

)
