package com.aboutme.network.mapping


import com.aboutme.fragment.AuthUserFragment
import com.aboutme.fragment.DiaryDataFragment
import com.aboutme.fragment.DreamDataFragment
import com.aboutme.fragment.DreamFragment
import com.aboutme.fragment.MoodDataFragment
import com.aboutme.fragment.SleepDataFragment
import com.aboutme.fragment.UserFragment
import com.aboutme.network.dto.AuthDataDto
import com.aboutme.network.dto.AuthUserDto
import com.aboutme.network.dto.DreamDto
import com.aboutme.network.dto.NameInfoDto
import com.aboutme.network.dto.UserDto
import com.aboutme.network.dto.daily.DiaryDataDto
import com.aboutme.network.dto.daily.DreamDataDto
import com.aboutme.network.dto.daily.MoodDataDto
import com.aboutme.network.dto.daily.SleepDataDto
import java.time.LocalDate

//
// Fragments
//

internal fun DiaryDataFragment.toDiaryData() =
    DiaryDataDto(diaryContent, date, updated, created)

internal fun SleepDataFragment.toSleepData() =
    SleepDataDto(hoursSlept.toInt(), hoursAim?.toInt(), date, created, updated)

internal fun MoodDataFragment.toMoodData() = MoodDataDto(
    mood?.toFloat(),
    moodMorning?.toFloat(),
    moodEvening?.toFloat(),
    moodNoon?.toFloat(),
    date,
    created,
    updated
)

internal fun DreamDataFragment.toDreamData() = DreamDataDto(
    date,
    created,
    updated
)

internal fun DreamFragment.toDream() =
    DreamDto(
        id,
        description,
        annotation,
        mood?.toFloat(),
        clearness?.toFloat(),
        date,
        created,
        updated
    )

internal fun AuthUserFragment.toAuthUser(): AuthUserDto = AuthUserDto(
    user = user.userFragment.toUserData(),
    authData = AuthDataDto(
        refreshToken = authData.authDataFragment.refreshToken,
        token = authData.authDataFragment.token
    )
)

fun UserFragment.toUserData() =
    UserDto(
        nameInfo = nameInfo.run {
            NameInfoDto(firstName, middleName, lastName, title)
        },
        email = email,
        createdAt = created,
        updatedAt = updated
    )