package com.aboutme.core.database.type

import androidx.room.TypeConverter
import java.time.Instant

object InstantTypeConverter {

    @TypeConverter
    fun toLong(instant: Instant?) = instant?.toEpochMilli()

    @TypeConverter
    fun fromLong(long: Long?) = long?.let { Instant.ofEpochMilli(long) }

}