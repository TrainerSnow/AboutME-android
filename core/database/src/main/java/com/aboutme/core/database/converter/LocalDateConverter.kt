package com.aboutme.core.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate

internal object LocalDateConverter {

    @TypeConverter
    fun toString(localDate: LocalDate?) = localDate?.let(LocalDate::toString)

    @TypeConverter
    fun fromString(string: String?) = string?.let(LocalDate::parse)

}