package com.aboutme.network.dto.daily

import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.type.DiaryDataInput
import java.time.Instant
import java.time.LocalDate


data class DiaryDataDto(

    val content: String,

    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant

) : SyncableDto {

    fun toInput() = DiaryDataInput(content, date, createdAt, updatedAt)

}
