package com.aboutme.network.dto.daily

import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.type.DreamDataInput
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class DreamDataDto(

    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val remoteId: UUID

) : SyncableDto {

    fun toInput() = DreamDataInput(
        date = date,
        updated = updatedAt,
        created = createdAt
    )

}
