package com.aboutme.core.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aboutme.core.model.daily.DailyDataCategory
import com.aboutme.core.ui.R

/**
 * Gets the resource id for the illustration the of [DailyDataCategory]
 */
val DailyDataCategory.illustration: Int
    @DrawableRes
    get() = when (this) {
        DailyDataCategory.SleepData -> R.drawable.daily_sleep
        DailyDataCategory.DreamData -> R.drawable.daily_dream
        DailyDataCategory.MoodData -> R.drawable.daily_mood
        DailyDataCategory.DiaryData -> R.drawable.daily_diary
    }

/**
 * Gets the resource id for the localized title of the [DailyDataCategory]
 */
val DailyDataCategory.title: Int
    @StringRes
    get() = when (this) {
        DailyDataCategory.SleepData -> R.string.daily_sleep_title
        DailyDataCategory.DreamData -> R.string.daily_dream_title
        DailyDataCategory.MoodData -> R.string.daily_mood_title
        DailyDataCategory.DiaryData -> R.string.daily_diary_title
    }

/**
 * Gets the resource id for the description of the [DailyDataCategory]
 */
val DailyDataCategory.description: Int
    @StringRes
    get() = when (this) {
        DailyDataCategory.SleepData -> R.string.daily_sleep_description
        DailyDataCategory.DreamData -> R.string.daily_dream_description
        DailyDataCategory.MoodData -> R.string.daily_mood_description
        DailyDataCategory.DiaryData -> R.string.daily_diary_description
    }