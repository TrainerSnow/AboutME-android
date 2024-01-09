package com.aboutme.core.model.base

import java.time.LocalDateTime

/**
 * Base for any model that has date information about itself
 */
interface DatedModel {

    /**
     * The time the item was created
     */
    val createdAt: LocalDateTime

    /**
     * The time the item has been updated
     */
    val updatedAt: LocalDateTime

}