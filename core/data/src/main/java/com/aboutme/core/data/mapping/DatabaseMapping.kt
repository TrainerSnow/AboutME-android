package com.aboutme.core.data.mapping

import com.aboutme.core.cache.entity.DreamEntity
import com.aboutme.core.cache.entity.daily.DiaryDataEntity
import com.aboutme.core.cache.entity.daily.MoodDataEntity
import com.aboutme.core.cache.entity.daily.SleepDataEntity
import com.aboutme.core.cache.entity.multi.DreamDataWithDreams
import com.aboutme.core.database.entity.SyncResultData
import com.aboutme.core.database.entity.SyncStatusEntity
import com.aboutme.core.database.entity.model.SyncTraffic
import com.aboutme.core.model.daily.data.DiaryData
import com.aboutme.core.model.daily.data.DreamData
import com.aboutme.core.model.daily.data.MoodData
import com.aboutme.core.model.daily.data.SleepData
import com.aboutme.core.model.data.Dream
import com.aboutme.core.model.sync.SyncResult
import com.aboutme.core.model.sync.SyncTrafficInfo

internal fun DiaryDataEntity.toModel() = DiaryData(content, createdAt, updatedAt, localId, remoteId)

internal fun SleepDataEntity.toModel() = SleepData(hoursSlept, hoursAim, createdAt, updatedAt, localId, remoteId)

internal fun DreamDataWithDreams.toModel() =
    DreamData(dreams.map { it.toModel() }, dreamData.createdAt, dreamData.updatedAt, dreamData.localId, dreamData.remoteId)

internal fun MoodDataEntity.toModel() =
    if (mood != null) MoodData.ConstantMoodData(mood!!, createdAt, updatedAt, localId, remoteId)
    else MoodData.VaryingMoodData(moodMorning!!, moodNoon!!, moodEvening!!, createdAt, updatedAt, localId, remoteId)

internal fun DreamEntity.toModel() =
    Dream(content, annotation, mood, clearness, createdAt, updatedAt, localId, remoteId)

internal fun Pair<SyncStatusEntity, SyncResultData?>.toSyncResult() =
    if (second == null) SyncResult.NotAuthorized(first.startedAt, first.finishedAt)
    else SyncResult.Success(
        started = first.startedAt,
        ended = first.finishedAt,
        moodDataTraffic = second!!.moodDataTraffic.toModel(),
        sleepDataTraffic = second!!.sleepDataTraffic.toModel(),
        dreamDataTraffic = second!!.dreamDataTraffic.toModel(),
        diaryDataTraffic = second!!.diaryDataTraffic.toModel(),
        userTraffic = second!!.userTraffic.toModel(),
        personsTraffic = second!!.personsTraffic.toModel(),
        dreamsTraffic = second!!.dreamTraffic.toModel(),
        relationsTraffic = second!!.relationsTraffic.toModel()
    )

internal fun SyncTraffic.toModel() = SyncTrafficInfo(
    serverAdded,
    serverDeleted,
    serverUpdated,
    localAdded,
    localDeleted,
    localUpdated
)

