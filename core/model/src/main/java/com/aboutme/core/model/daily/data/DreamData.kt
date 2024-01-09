package com.aboutme.core.model.daily.data

import com.aboutme.core.model.daily.DailyDataCategory
import com.aboutme.core.model.data.Dream
import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable

/**
 * A data for one day that stores the dreams a user has has
 */
data class DreamData(

    /**
     * The dreams the user has had
     */
    val dreams: List<Dream>

): Sortable<DreamData> {

    override fun comparatorFor(mode: SortMode): Comparator<DreamData>? = when (mode) {
        SortMode.Length -> compareBy { it.dreams.size }
        else -> null
    }

}
