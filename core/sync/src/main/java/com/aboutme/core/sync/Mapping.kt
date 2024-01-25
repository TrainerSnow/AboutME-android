package com.aboutme.core.sync

import com.aboutme.core.cache.entity.DreamEntity
import com.aboutme.core.cache.entity.UserEntity
import com.aboutme.core.cache.entity.daily.DiaryDataEntity
import com.aboutme.core.cache.entity.daily.DreamDataEntity
import com.aboutme.core.cache.entity.daily.MoodDataEntity
import com.aboutme.core.cache.entity.daily.SleepDataEntity
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.network.dto.DreamDto
import com.aboutme.network.dto.NameInfoDto
import com.aboutme.network.dto.daily.DiaryDataDto
import com.aboutme.network.dto.daily.DreamDataDto
import com.aboutme.network.dto.daily.MoodDataDto
import com.aboutme.network.dto.daily.SleepDataDto
import com.aboutme.network.dto.update.UpdateDreamDto
import com.aboutme.network.dto.update.UpdateUserDto

fun DiaryDataEntity.toDto() = DiaryDataDto(content, date, createdAt, updatedAt, remoteId)

fun DiaryDataDto.toEntity(localId: Long?) = DiaryDataEntity(date, content, createdAt, updatedAt, null, remoteId, localId)

fun SleepDataEntity.toDto() = SleepDataDto(hoursSlept, hoursAim, date, createdAt, updatedAt, remoteId)

fun SleepDataDto.toEntity(localId: Long?) = SleepDataEntity(date, hoursSlept, hoursAim, createdAt, updatedAt, null, remoteId, localId)

fun MoodDataEntity.toDto() = MoodDataDto(mood, moodMorning, moodEvening, moodNoon, date, createdAt, updatedAt, remoteId)

fun MoodDataDto.toEntity(localId: Long?) = MoodDataEntity(date, mood, moodMorning, moodNoon, moodEvening, createdAt, updatedAt, null, remoteId, localId)

fun DreamDataEntity.toDto() = DreamDataDto(date, createdAt, updatedAt, remoteId)

fun DreamDataDto.toEntity(localId: Long?) = DreamDataEntity(date, createdAt, updatedAt, null, remoteId, localId)

fun DreamEntity.toDto() = DreamDto(content, annotation, mood, clearness, date, createdAt, updatedAt, remoteId)

fun DreamEntity.toUpdateDto() = UpdateDreamDto(content, annotation, mood, clearness, date, updatedAt)

fun DreamDto.toEntity(localId: Long?) = DreamEntity(
    date,
    content,
    annotation,
    mood,
    clearness,
    createdAt,
    updatedAt,
    null,
    remoteId,
    localId
)

fun UserEntity.toUpdateDto() = UpdateUserDto(nameInfo.toDto(), updatedAt)

fun UserData.toEntity() = UserEntity(email, nameInfo, createdAt, updatedAt)

fun NameInfo.toDto() = NameInfoDto(firstName, middleName, lastName, title)

