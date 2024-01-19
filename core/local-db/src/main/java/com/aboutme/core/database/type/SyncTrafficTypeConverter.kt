package com.aboutme.core.database.type

import androidx.room.TypeConverter
import com.aboutme.core.database.entity.model.SyncTraffic
import com.google.gson.Gson

object SyncTrafficTypeConverter {

    @TypeConverter
    fun toString(syncTraffic: SyncTraffic?) = syncTraffic?.let {
        Gson()
            .toJson(it)
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let {
        Gson()
            .fromJson(string, SyncTraffic::class.java)
    }

}