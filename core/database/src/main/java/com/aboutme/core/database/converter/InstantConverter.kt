package com.aboutme.core.database.converter

import androidx.room.TypeConverter
import java.time.Instant

internal object InstantConverter {

    @TypeConverter
    fun toString(ins: Instant?): String? = ins?.toString()

    @TypeConverter
    fun fromString(str: String?): Instant? = str?.let { Instant.parse(it) }

}