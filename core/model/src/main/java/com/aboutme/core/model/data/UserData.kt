package com.aboutme.core.model.data

import com.aboutme.core.model.base.DatedModel
import java.time.Instant

/**
 * The data about the current user
 */
data class UserData(

    /**
     * The naming preferences of the user
     */
    val nameInfo: NameInfo,

    /**
     * The email of the user
     */
    val email: String,

    override val createdAt: Instant,

    override val updatedAt: Instant

): DatedModel
