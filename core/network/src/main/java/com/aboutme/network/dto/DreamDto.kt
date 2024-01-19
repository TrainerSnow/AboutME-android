package com.aboutme.network.dto

import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.type.CreateDreamInput
import com.apollographql.apollo3.api.Optional
import java.time.Instant
import java.time.LocalDate


data class DreamDto(

    val id: Long,

    val content: String,

    val annotation: String?,

    val mood: Float?,

    val clearness: Float?,

    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant

): SyncableDto {

     fun toAddInput() = CreateDreamInput(
         description = content,
         annotation = Optional.presentIfNotNull(annotation),
         clearness = Optional.presentIfNotNull(clearness?.toDouble()),
         mood = Optional.presentIfNotNull(mood?.toDouble()),
         date = date,
         updated = updatedAt,
         created = createdAt
     )

}

