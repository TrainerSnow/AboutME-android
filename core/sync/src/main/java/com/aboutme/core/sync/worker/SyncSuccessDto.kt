package com.aboutme.core.sync.worker

import com.aboutme.core.database.entity.model.SyncTraffic
import java.time.Instant

internal data class SyncSuccessDto(

    val start: Instant,

    val end: Instant,

    val diaryDataTraffic: SyncTraffic,

    val sleepDataTraffic: SyncTraffic,

    val moodDataTraffic: SyncTraffic,

    val dreamDataTraffic: SyncTraffic,

    val dreamTraffic: SyncTraffic,

    val personsTraffic: SyncTraffic,

    val relationsTraffic: SyncTraffic,

    val userTraffic: SyncTraffic

)
