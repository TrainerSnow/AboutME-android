package com.aboutme.core.model.data

import com.aboutme.core.model.base.DatedModel
import com.aboutme.core.model.base.IdentifiableModel
import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable
import java.time.Instant
import java.util.UUID

/**
 * Represents a dream of a user
 */
data class Dream(

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
    val clearness: Float?,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val localId: Long?,

    override val remoteId: UUID?

): Sortable<Dream>, DatedModel, IdentifiableModel {

    override fun comparatorFor(mode: SortMode): Comparator<Dream>? = when (mode) {
        SortMode.Length -> compareBy { it.content.length }
        else -> null
    }

}
