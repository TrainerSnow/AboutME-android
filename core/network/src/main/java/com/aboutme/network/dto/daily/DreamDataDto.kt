package com.aboutme.network.dto.daily

import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.type.DreamDataInput
import java.time.Instant
import java.time.LocalDate

data class DreamDataDto(

    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant

) : SyncableDto {

    fun toInput() = DreamDataInput(
        date = date,
        updated = updatedAt,
        created = createdAt
    )

}
