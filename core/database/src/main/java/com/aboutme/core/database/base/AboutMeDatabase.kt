package com.aboutme.core.database.base;

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aboutme.core.database.converter.InstantConverter
import com.aboutme.core.database.converter.LocalDateConverter
import com.aboutme.core.database.converter.NameInfoConverter
import com.aboutme.core.database.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
@TypeConverters(
    value = [NameInfoConverter::class, LocalDateConverter::class, InstantConverter::class]
)
abstract class AboutMeDatabase : RoomDatabase()