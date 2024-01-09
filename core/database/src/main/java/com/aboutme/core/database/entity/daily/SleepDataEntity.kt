package com.aboutme.core.database.entity.daily

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "sleep_data"
)
data class SleepDataEntity(

    @PrimaryKey
    val date: LocalDate,

    val hoursSlept: Int,

    val hoursAim: Int?

)
