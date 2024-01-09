package com.aboutme.core.model.daily

import com.aboutme.core.model.daily.data.DiaryData
import com.aboutme.core.model.daily.data.DreamData
import com.aboutme.core.model.daily.data.MoodData
import com.aboutme.core.model.daily.data.SleepData
import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable

/**
 * Bundles all daily datas for one day
 */
data class DailyData(

    /**
     * The daily [DiaryData]
     */
    val diaryData: DailyDataInfo<DiaryData>,


    /**
     * The daily [DreamData]
     */
    val dreamData: DailyDataInfo<DreamData>,


    /**
     * The daily [SleepData]
     */
    val sleepData: DailyDataInfo<SleepData>,


    /**
     * The daily [MoodData]
     */
    val moodData: DailyDataInfo<MoodData>

) : Sortable<DailyData> {

    /**
     * gets all the datas for that day as a set
     */
    fun all(): Set<DailyDataInfo<*>> = setOf(diaryData, dreamData, sleepData, moodData)

    fun getByCategory(category: DailyDataCategory) = all().find { it.category == category }

    override fun comparatorFor(mode: SortMode): Comparator<DailyData>? = when (mode) {
        SortMode.Amount -> compareBy { all().filter { it.data != null }.size }
        SortMode.Completed -> compareBy { all().all { it.data != null } }
        SortMode.Date -> compareBy { diaryData.date }
        else -> null
    }

}
