package com.aboutme.network.mapping

import com.aboutme.core.model.daily.DailyData
import com.aboutme.core.model.daily.DailyDataCategory
import com.aboutme.core.model.daily.DailyDataInfo
import com.aboutme.core.model.daily.data.DiaryData
import com.aboutme.core.model.daily.data.DreamData
import com.aboutme.core.model.daily.data.MoodData
import com.aboutme.core.model.daily.data.SleepData
import com.aboutme.core.model.data.AuthData
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.Dream
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.fragment.AuthUserFragment
import com.aboutme.fragment.DayDataFragment
import com.aboutme.fragment.DiaryDataFragment
import com.aboutme.fragment.DreamDataFragment
import com.aboutme.fragment.DreamFragment
import com.aboutme.fragment.MoodDataFragment
import com.aboutme.fragment.SleepDataFragment
import com.aboutme.fragment.UserFragment

internal fun DayDataFragment.toDailyData() = DailyData(
    diaryData = DailyDataInfo(
        data = diaryData?.diaryDataFragment?.toDiaryData(),
        date = date,
        category = DailyDataCategory.DiaryData
    ),
    dreamData = DailyDataInfo(
        data = dreamData?.dreamDataFragment?.toDreamData(),
        date = date,
        category = DailyDataCategory.DreamData
    ),
    sleepData = DailyDataInfo(
        data = sleepData?.sleepDataFragment?.toSleepData(),
        date = date,
        category = DailyDataCategory.SleepData
    ),
    moodData = DailyDataInfo(
        data = moodData?.moodDataFragment?.toMoodData(),
        date = date,
        category = DailyDataCategory.MoodData
    ),
)

internal fun DiaryDataFragment.toDiaryData() = DiaryData(diaryContent, updated, created)

internal fun SleepDataFragment.toSleepData() = SleepData(hoursSlept.toInt(), hoursAim?.toInt(), created, updated)

internal fun MoodDataFragment.toMoodData() =
    if (mood != null) MoodData.ConstantMoodData(mood.toFloat(), created, updated)
    else MoodData.VaryingMoodData(
        moodMorning!!.toFloat(),
        moodNoon!!.toFloat(),
        moodEvening!!.toFloat(),
        created,
        updated
    )

internal fun DreamDataFragment.toDreamData() = DreamData(
    dreams = dreams.map { it.dreamFragment.toDream() },
    created,
    updated
)

internal fun DreamFragment.toDream() =
    Dream(id, description, annotation, mood?.toFloat(), clearness?.toFloat(), created, updated)

internal fun AuthUserFragment.toAuthUser(): AuthUser = AuthUser(
    user = user.userFragment.toUserData(),
    authData = AuthData(
        refreshToken = authData.authDataFragment.refreshToken,
        token = authData.authDataFragment.token
    )
)

fun UserFragment.toUserData(): UserData =
    UserData(
        nameInfo = nameInfo.run {
            NameInfo(firstName, middleName, lastName, title)
        },
        email = email,
        createdAt = created,
        updatedAt = updated
    )