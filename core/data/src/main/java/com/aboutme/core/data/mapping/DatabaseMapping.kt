package com.aboutme.core.data.mapping

import com.aboutme.core.database.entity.DreamEntity
import com.aboutme.core.database.entity.daily.DiaryDataEntity
import com.aboutme.core.database.entity.daily.MoodDataEntity
import com.aboutme.core.database.entity.daily.SleepDataEntity
import com.aboutme.core.database.entity.multi.DreamDataWithDreams
import com.aboutme.core.model.daily.data.DiaryData
import com.aboutme.core.model.daily.data.DreamData
import com.aboutme.core.model.daily.data.MoodData
import com.aboutme.core.model.daily.data.SleepData
import com.aboutme.core.model.data.Dream

internal fun DiaryDataEntity.toModel() = DiaryData(content, createdAt, updatedAt)

internal fun SleepDataEntity.toModel() = SleepData(hoursSlept, hoursAim, createdAt, updatedAt)

internal fun DreamDataWithDreams.toModel() =
    DreamData(dreams.map { it.toModel() }, dreamData.createdAt, dreamData.updatedAt)

internal fun MoodDataEntity.toModel() =
    if (mood != null) MoodData.ConstantMoodData(mood!!, createdAt, updatedAt)
    else MoodData.VaryingMoodData(moodMorning!!, moodNoon!!, moodEvening!!, createdAt, updatedAt)

internal fun DreamEntity.toModel() =
    Dream(id!!, content, annotation, mood, clearness, createdAt, updatedAt)