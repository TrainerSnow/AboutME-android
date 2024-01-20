package com.aboutme.core.database.entity.multi

import androidx.room.Embedded
import androidx.room.Relation
import com.aboutme.core.database.entity.SyncResultData
import com.aboutme.core.database.entity.SyncStatusEntity

data class SyncStatusWithResult(

    @Embedded
    val syncStatus: SyncStatusEntity,

    @Relation(
        parentColumn = "startedAt",
        entityColumn = "syncStatusEntityId"
    )
    val syncResult: SyncResultData? = null

)
