package com.aboutme.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.database.entity.model.SyncTraffic
import java.time.Instant

@Entity(
    tableName = "sync_result_data"
)
data class SyncResultData(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val syncStatusEntityId: Instant,

    val diaryDataTraffic: SyncTraffic,

    val sleepDataTraffic: SyncTraffic,

    val moodDataTraffic: SyncTraffic,

    val dreamDataTraffic: SyncTraffic,

    val dreamTraffic: SyncTraffic,

    val personsTraffic: SyncTraffic,

    val relationsTraffic: SyncTraffic,

    val userTraffic: SyncTraffic

)
