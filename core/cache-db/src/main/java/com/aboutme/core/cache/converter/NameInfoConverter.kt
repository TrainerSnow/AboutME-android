package com.aboutme.core.cache.converter

import androidx.room.TypeConverter
import com.aboutme.core.model.data.NameInfo
import com.google.gson.Gson


internal object NameInfoConverter {

    @TypeConverter
    fun toString(nameInfo: NameInfo?) = nameInfo?.let {
        Gson()
            .toJsonTree(nameInfo)
            .toString()
    }

    @TypeConverter
    fun fromString(string: String?) = string?.run {
        Gson()
            .fromJson(this, NameInfo::class.java)
    }

}