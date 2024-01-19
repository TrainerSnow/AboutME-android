package com.aboutme.core.cache.base;

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aboutme.core.cache.converter.InstantConverter
import com.aboutme.core.cache.converter.LocalDateConverter
import com.aboutme.core.cache.converter.NameInfoConverter
import com.aboutme.core.cache.dao.DiaryDataDao
import com.aboutme.core.cache.dao.DreamDao
import com.aboutme.core.cache.dao.DreamDataDao
import com.aboutme.core.cache.dao.MoodDataDao
import com.aboutme.core.cache.dao.SleepDataDao
import com.aboutme.core.cache.dao.UserDao
import com.aboutme.core.cache.entity.DreamEntity
import com.aboutme.core.cache.entity.UserEntity
import com.aboutme.core.cache.entity.daily.DiaryDataEntity
import com.aboutme.core.cache.entity.daily.DreamDataEntity
import com.aboutme.core.cache.entity.daily.MoodDataEntity
import com.aboutme.core.cache.entity.daily.SleepDataEntity

@Database(
    entities = [UserEntity::class, DreamEntity::class, DiaryDataEntity::class, DreamDataEntity::class, MoodDataEntity::class, SleepDataEntity::class],
    version = 3
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