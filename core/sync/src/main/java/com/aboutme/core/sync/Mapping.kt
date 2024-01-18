package com.aboutme.core.sync

import com.aboutme.core.database.entity.DreamEntity
import com.aboutme.core.database.entity.UserEntity
import com.aboutme.core.database.entity.daily.DiaryDataEntity
import com.aboutme.core.database.entity.daily.DreamDataEntity
import com.aboutme.core.database.entity.daily.MoodDataEntity
import com.aboutme.core.database.entity.daily.SleepDataEntity
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

fun DiaryDataEntity.toDto() = DiaryDataDto(content, date, createdAt, updatedAt)

fun DiaryDataDto.toEntity() = DiaryDataEntity(date, content, createdAt, updatedAt, null)

fun SleepDataEntity.toDto() = SleepDataDto(hoursSlept, hoursAim, date, createdAt, updatedAt)

fun SleepDataDto.toEntity() = SleepDataEntity(date, hoursSlept, hoursAim, createdAt, updatedAt, null)

fun MoodDataEntity.toDto() = MoodDataDto(mood, moodMorning, moodEvening, moodNoon, date, createdAt, updatedAt)

fun MoodDataDto.toEntity() = MoodDataEntity(date, mood, moodMorning, moodNoon, moodEvening, createdAt, updatedAt, null)

fun DreamDataEntity.toDto() = DreamDataDto(date, createdAt, updatedAt)

fun DreamDataDto.toEntity() = DreamDataEntity(date, createdAt, updatedAt, null)

fun DreamEntity.toDto() = DreamDto(id!!, content, annotation, mood, clearness, date, createdAt, updatedAt)

fun DreamEntity.toUpdateDto() = UpdateDreamDto(content, annotation, mood, clearness, date, updatedAt)

fun DreamDto.toEntity() = DreamEntity(id, date, content, annotation, mood, clearness, createdAt, updatedAt, null)

fun UserEntity.toUpdateDto() = UpdateUserDto(nameInfo.toDto(), updatedAt)

fun UserData.toEntity() = UserEntity(email, nameInfo, createdAt, updatedAt)

fun NameInfo.toDto() = NameInfoDto(firstName, middleName, lastName, title)

