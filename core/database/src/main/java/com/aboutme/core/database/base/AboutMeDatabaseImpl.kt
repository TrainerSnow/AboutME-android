package com.aboutme.core.database.base;

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aboutme.core.database.converter.InstantConverter
import com.aboutme.core.database.converter.LocalDateConverter
import com.aboutme.core.database.converter.NameInfoConverter
import com.aboutme.core.database.dao.DiaryDataDao
import com.aboutme.core.database.dao.DreamDao
import com.aboutme.core.database.dao.DreamDataDao
import com.aboutme.core.database.dao.MoodDataDao
import com.aboutme.core.database.dao.SleepDataDao
import com.aboutme.core.database.dao.UserDao
import com.aboutme.core.database.entity.DreamEntity
import com.aboutme.core.database.entity.UserEntity
import com.aboutme.core.database.entity.daily.DiaryDataEntity
import com.aboutme.core.database.entity.daily.DreamDataEntity
import com.aboutme.core.database.entity.daily.MoodDataEntity
import com.aboutme.core.database.entity.daily.SleepDataEntity

@Database(
    entities = [UserEntity::class, DreamEntity::class, DiaryDataEntity::class, DreamDataEntity::class, MoodDataEntity::class, SleepDataEntity::class],
    version = 1
)
@TypeConverters(
    value = [NameInfoConverter::class, LocalDateConverter::class, InstantConverter::class]
)
abstract class AboutMeDatabaseImpl : RoomDatabase(), AboutMeDatabase {

    abstract val diaryDataDao: DiaryDataDao

    abstract val dreamDataDao: DreamDataDao

    abstract val sleepDataDao: SleepDataDao

    abstract val moodDataDao: MoodDataDao

    abstract val dreamDao: DreamDao

    abstract val userDao: UserDao

    override fun deleteAllRows() {
        this.clearAllTables()
    }

}