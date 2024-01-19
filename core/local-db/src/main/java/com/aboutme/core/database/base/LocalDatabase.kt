package com.aboutme.core.database.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aboutme.core.database.dao.SyncStatusDao
import com.aboutme.core.database.entity.SyncResultData
import com.aboutme.core.database.entity.SyncStatusEntity
import com.aboutme.core.database.type.InstantTypeConverter
import com.aboutme.core.database.type.SyncTrafficTypeConverter

@Database(
    version = LocalDatabase.DB_VERSION,
    entities = [SyncStatusEntity::class, SyncResultData::class]
)
@TypeConverters(
    value = [InstantTypeConverter::class, SyncTrafficTypeConverter::class]
)
abstract class LocalDatabase : RoomDatabase() {

    abstract val syncStatusDao: SyncStatusDao

    companion object {

        const val DB_NAME = "aboutme-local-db"

        const val DB_VERSION = 1

    }

}
