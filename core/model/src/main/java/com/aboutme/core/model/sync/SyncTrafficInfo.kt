package com.aboutme.core.model.sync

/**
 * Data for one specific set of entities that were synced
 */
data class SyncTrafficInfo(

    val servedAdded: Int,

    val serverDeleted: Int,

    val serverUpdated: Int,

    val localAdded: Int,

    val localDeleted: Int,

    val localUpdated: Int

)
