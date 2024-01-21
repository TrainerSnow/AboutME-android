package com.aboutme.core.sync.worker

import com.aboutme.core.database.entity.model.SyncTraffic

internal data class SyncSuccessDto(

    val start: Long,

    val end: Long,

    val diaryDataTraffic: SyncTraffic,

    val sleepDataTraffic: SyncTraffic,

    val moodDataTraffic: SyncTraffic,

    val dreamDataTraffic: SyncTraffic,

    val dreamTraffic: SyncTraffic,

    val personsTraffic: SyncTraffic,

    val relationsTraffic: SyncTraffic,

    val userTraffic: SyncTraffic

)
