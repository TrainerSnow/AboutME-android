package com.aboutme.core.ui.time

import android.text.format.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.DurationUnit

fun LocalDateTime.formatDateFirst(): String {
    val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), "d MMMM H m")
    val formatter = DateTimeFormatter
        .ofPattern(pattern)
        .withLocale(Locale.getDefault())

    return format(formatter)
}

fun Duration.format(): String {
    val unit = if (inWholeNanoseconds < 1000) DurationUnit.NANOSECONDS
    else if (inWholeMicroseconds < 1000) DurationUnit.MICROSECONDS
    else if (inWholeMilliseconds < 1000) DurationUnit.MICROSECONDS
    else if (inWholeSeconds < 60) DurationUnit.SECONDS
    else if (inWholeMinutes < 60) DurationUnit.MINUTES
    else if (inWholeHours < 24) DurationUnit.HOURS
    else DurationUnit.DAYS

    return toString(unit)
}