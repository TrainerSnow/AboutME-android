package com.aboutme.network.dto.daily

import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.type.DiaryDataInput
import java.time.Instant
import java.time.LocalDate
import java.util.UUID


data class DiaryDataDto(

    val content: String,

    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val remoteId: UUID

) : SyncableDto {

    fun toInput() = DiaryDataInput(content, date, createdAt, updatedAt)

}
