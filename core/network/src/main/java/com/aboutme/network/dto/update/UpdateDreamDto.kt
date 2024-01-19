package com.aboutme.network.dto.update

import com.aboutme.network.dto.base.UpdateDto
import com.aboutme.type.UpdateDreamInput
import com.apollographql.apollo3.api.Optional
import java.time.Instant
import java.time.LocalDate

data class UpdateDreamDto(

    val content: String,

    val annotation: String?,

    val mood: Float?,

    val clearness: Float?,

    val date: LocalDate,

    override val updatedAt: Instant

) : UpdateDto {

    fun toUpdateInput() = UpdateDreamInput(
        description = content,
        annotation = Optional.presentIfNotNull(annotation),
        clearness = Optional.presentIfNotNull(clearness?.toDouble()),
        mood = Optional.presentIfNotNull(mood?.toDouble()),
        date = date,
        updated = updatedAt
    )

}
