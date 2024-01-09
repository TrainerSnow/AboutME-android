package com.aboutme.core.data.implementation;

import com.aboutme.core.data.repository.DailyDataRepository
import com.aboutme.core.database.dao.DiaryDataDao
import com.aboutme.core.database.dao.DreamDataDao
import com.aboutme.core.database.dao.MoodDataDao
import com.aboutme.core.database.dao.SleepDataDao
import com.aboutme.core.database.entity.DreamEntity
import com.aboutme.core.database.entity.daily.DiaryDataEntity
import com.aboutme.core.database.entity.daily.MoodDataEntity
import com.aboutme.core.database.entity.daily.SleepDataEntity
import com.aboutme.core.database.entity.multi.DreamDataWithDreams
import com.aboutme.core.model.daily.DailyData
import com.aboutme.core.model.daily.DailyDataCategory
import com.aboutme.core.model.daily.DailyDataInfo
import com.aboutme.core.model.daily.data.DiaryData
import com.aboutme.core.model.daily.data.DreamData
import com.aboutme.core.model.daily.data.MoodData
import com.aboutme.core.model.daily.data.SleepData
import com.aboutme.core.model.data.Dream
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

internal class OfflineDailyDataRepository(
    private val diaryDataDao: DiaryDataDao,
    private val sleepDataDao: SleepDataDao,
    private val dreamDataDao: DreamDataDao,
    private val moodDataDao: MoodDataDao
) : DailyDataRepository {

    override fun getForDay(date: LocalDate) = combine(
        flow = diaryDataDao.getByDate(date),
        flow2 = sleepDataDao.getByDate(date),
        flow3 = dreamDataDao.getWithDreamsByDate(date),
        flow4 = moodDataDao.getByDate(date)
    ) { diary, sleep, dream, mood ->
        DailyData(
            diaryData = DailyDataInfo(diary?.toModel(), date, DailyDataCategory.DiaryData),
            sleepData = DailyDataInfo(sleep?.toModel(), date, DailyDataCategory.SleepData),
            dreamData = DailyDataInfo(dream?.toModel(), date, DailyDataCategory.DreamData),
            moodData = DailyDataInfo(mood?.toModel(), date, DailyDataCategory.MoodData),
        )
    }

}

private fun DiaryDataEntity.toModel() = DiaryData(content, createdAt, updatedAt)

private fun SleepDataEntity.toModel() = SleepData(hoursSlept, hoursAim, createdAt, updatedAt)

private fun DreamDataWithDreams.toModel() =
    DreamData(dreams.map { it.toModel() }, dreamData.createdAt, dreamData.updatedAt)

private fun MoodDataEntity.toModel() =
    if (mood != null) MoodData.ConstantMoodData(mood!!, createdAt, updatedAt)
    else MoodData.VaryingMoodData(moodMorning!!, moodNoon!!, moodEvening!!, createdAt, updatedAt)

private fun DreamEntity.toModel() =
    Dream(id!!, content, annotation, mood, clearness, createdAt, updatedAt)