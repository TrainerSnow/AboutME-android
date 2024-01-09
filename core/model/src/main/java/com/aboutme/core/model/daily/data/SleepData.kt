package com.aboutme.core.model.daily.data

import com.aboutme.core.model.base.DatedModel
import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable
import java.time.Instant

/**
 * Data for one day that stores how the user slept
 */
data class SleepData(

    /**
     * The amount of hours the user slept
     */
    val hoursSlept: Int,

    /**
     * The amount of hours the user wanted to sleep
     */
    val hoursAim: Int?,

    override val createdAt: Instant,

    override val updatedAt: Instant

): Sortable<SleepData>, DatedModel {

    override fun comparatorFor(mode: SortMode): Comparator<SleepData>? = when (mode) {
        SortMode.Amount -> compareBy { it.hoursAim }
        else -> null
    }

}
