package com.aboutme.core.cache.entity.base

import java.time.Instant
import java.util.UUID

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

    /**
     * The id of the entity in the remote storage. Null if it has not yet been synced
     */
    val remoteId: UUID?

    /**
     * The id of the entity in the local cache. Null means it has not yet been saved
     */
    val localId: Long?

}