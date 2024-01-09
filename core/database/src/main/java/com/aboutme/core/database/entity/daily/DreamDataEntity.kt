package com.aboutme.core.database.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "dream_data"
)
data class DreamDataEntity(

    @PrimaryKey
    val date: LocalDate

)
