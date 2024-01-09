package com.aboutme.core.model.daily.data

import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable

/**
 * A data for one day that stores the diary entry for the user
 */
data class DiaryData(

    /**
     * The content of the diary
     */
    val content: String

): Sortable<DiaryData> {

    override fun comparatorFor(mode: SortMode) = when (mode) {
        SortMode.Length -> compareBy<DiaryData> { it.content.length }
        else -> null
    }

}