package com.aboutme.core.sync.worker

import java.time.Instant


internal data class SyncErrorDto (

    val start: Instant,

    val end: Instant

)
