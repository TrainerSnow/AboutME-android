package com.aboutme.core.data.implementation;

import com.aboutme.core.cache.dao.DiaryDataDao
import com.aboutme.core.cache.dao.DreamDataDao
import com.aboutme.core.cache.dao.MoodDataDao
import com.aboutme.core.cache.dao.SleepDataDao
import com.aboutme.core.data.mapping.toModel
import com.aboutme.core.data.repository.DailyDataRepository
import com.aboutme.core.model.daily.DailyData
import com.aboutme.core.model.daily.DailyDataCategory
import com.aboutme.core.model.daily.DailyDataInfo
import com.aboutme.core.model.daily.DailyDataProgress
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
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

    override fun getProgress(date: LocalDate) = getForDay(date)
        .map {
            DailyDataProgress(
                completed = it.all().count { it.data != null },
                total = it.all().size
            )
        }

}

