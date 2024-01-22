package com.aboutme.core.common.time

import java.time.Instant
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Instant.durationTo(other: Instant): Duration {
    val millisDif = other.toEpochMilli() - toEpochMilli()
    return millisDif.toDuration(DurationUnit.MILLISECONDS)
}