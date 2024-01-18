package com.aboutme.network.dto.daily

import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.type.MoodDataInput
import com.apollographql.apollo3.api.Optional
import java.time.Instant
import java.time.LocalDate


data class MoodDataDto(

    val mood: Float? = null,

    val moodMorning: Float? = null,

    val moodEvening: Float? = null,

    val moodNoon: Float? = null,

    val date: LocalDate,

    override val createdAt: Instant,

    override val updatedAt: Instant

) : SyncableDto {

    fun toInput() = MoodDataInput(
        mood = Optional.presentIfNotNull(mood?.toDouble()),
        moodMorning = Optional.presentIfNotNull(moodMorning?.toDouble()),
        moodNoon = Optional.presentIfNotNull(moodNoon?.toDouble()),
        moodEvening = Optional.presentIfNotNull(moodEvening?.toDouble()),
        date = date,
        created = createdAt,
        updated = updatedAt
    )

}
