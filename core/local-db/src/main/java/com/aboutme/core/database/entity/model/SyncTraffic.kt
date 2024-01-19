package com.aboutme.core.database.entity.model

data class SyncTraffic(

    val serverAdded: Int = 0,

    val localAdded: Int = 0,

    val serverUpdated: Int = 0,

    val localUpdated: Int = 0,

    val serverDeleted: Int = 0,

    val localDeleted: Int = 0

)
