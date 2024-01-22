package com.aboutme.core.ui.time

import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime

fun Instant.toDeviceDate(): LocalDateTime {
    val zoneOffset = OffsetDateTime.now().offset
    val date = atOffset(zoneOffset)
    return date.toLocalDateTime()
}