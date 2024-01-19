package com.aboutme.network.dto.daily

import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.type.SleepDataInput
import com.apollographql.apollo3.api.Optional
import java.time.Instant
import java.time.LocalDate


data class SleepDataDto(

    val hoursSlept: Int,

    val hoursAim: Int? = null,

    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant

) : SyncableDto {

    fun toInput() = SleepDataInput(
        hoursSlept = hoursSlept.toDouble(),
        hoursAim = Optional.presentIfNotNull(hoursAim?.toDouble()),
        date = date,
        created = createdAt,
        updated = updatedAt
    )

}
