package com.aboutme.core.model.data

import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable

/**
 * Represents a dream of a user
 */
data class Dream(

    /**
     * The unique id
     */
    val id: Long,

    /**
     * The content, aka what happened in the dream
     */
    val content: String,

    /**
     * An optional annotation about the dream
     */
    val annotation: String?,

    /**
     * How the user felt during the dream
     */
    val mood: Float?,

    /**
     * How clear the memory of the dream is
     */
    val clearness: Float?

): Sortable<Dream> {

    override fun comparatorFor(mode: SortMode): Comparator<Dream>? = when (mode) {
        SortMode.Length -> compareBy { it.content.length }
        else -> null
    }

}
