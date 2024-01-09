package com.aboutme.core.model.base

import java.time.Instant

/**
 * Base for any model that has date information about itself
 */
interface DatedModel {

    /**
     * The time the item was created. This is a UTC timestamp
     */
    val createdAt: Instant

    /**
     * The time the item has been updated. This is a UTC timestamp
     */
    val updatedAt: Instant

}