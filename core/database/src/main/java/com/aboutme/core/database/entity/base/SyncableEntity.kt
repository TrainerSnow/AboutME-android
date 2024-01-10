package com.aboutme.core.database.entity.base

import java.time.Instant

/**
 * Base for any entity that can be synced to the server
 */
interface SyncableEntity {

    /**
     * Creation timestamp
     */
    val createdAt: Instant

    /**
     * Deletion timestamp. Null if it has not been deleted
     */
    val deletedAt: Instant?

    /**
     * Update timestamp
     */
    val updatedAt: Instant

}