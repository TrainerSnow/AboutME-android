package com.aboutme.core.model.daily

import com.aboutme.core.model.daily.data.DreamData
import com.aboutme.core.model.daily.data.SleepData
import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable
import java.time.LocalDate

/**
 * Additional information about one daily data
 */
data class DailyDataInfo<T>(

    /**
     * The actual data, e.g. [SleepData] or [DreamData], or null, if it doesn't yet exist
     */
    val data: T?,

    /**
     * The date for which the data was
     */
    val date: LocalDate,

    /**
     * The category
     */
    val category: DailyDataCategory


): Sortable<DailyDataInfo<T>> {

    override fun comparatorFor(mode: SortMode): Comparator<DailyDataInfo<T>>? = when (mode) {
        SortMode.Completed -> compareBy { it.data != null }
        SortMode.Date -> compareBy { it.date }
        SortMode.Category -> compareBy { it.category }
        else -> null
    }

}
