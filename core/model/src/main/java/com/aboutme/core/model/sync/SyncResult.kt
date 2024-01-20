package com.aboutme.core.model.sync

import java.time.Instant

sealed interface SyncResult {

    val started: Instant

    val ended: Instant

    /**
     * Sync was not executed because the user was not authorized
     */
    data class NotAuthorized(
        override val started: Instant,
        override val ended: Instant
    ) : SyncResult

    /**
     * Sync was successful. Contains exact data of how much was deleted/added/updated on each side
     */
    data class Success(

        override val started: Instant,

        override val ended: Instant,

        val moodDataTraffic: SyncTrafficInfo,

        val sleepDataTraffic: SyncTrafficInfo,

        val dreamDataTraffic: SyncTrafficInfo,

        val diaryDataTraffic: SyncTrafficInfo,

        val userTraffic: SyncTrafficInfo,

        val dreamsTraffic: SyncTrafficInfo,

        val personsTraffic: SyncTrafficInfo,

        val relationsTraffic: SyncTrafficInfo

    ) : SyncResult

}
