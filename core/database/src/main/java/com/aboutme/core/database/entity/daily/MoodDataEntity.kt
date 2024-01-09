package com.aboutme.core.database.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
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

    val moodEvening: Float?

)
