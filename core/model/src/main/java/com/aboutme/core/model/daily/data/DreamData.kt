package com.aboutme.core.model.daily.data

import com.aboutme.core.model.base.DatedModel
import com.aboutme.core.model.daily.DailyDataCategory
import com.aboutme.core.model.data.Dream
import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable
import java.time.Instant

/**
 * A data for one day that stores the dreams a user has has
 */
data class DreamData(

    /**
     * The dreams the user has had
     */
    val dreams: List<Dream>,

    override val createdAt: Instant,

    override val updatedAt: Instant

): Sortable<DreamData>, DatedModel {

    override fun comparatorFor(mode: SortMode): Comparator<DreamData>? = when (mode) {
        SortMode.Length -> compareBy { it.dreams.size }
        else -> null
    }

}
