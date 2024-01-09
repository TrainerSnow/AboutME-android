package com.aboutme.core.model

/**
 * Contains information about the streak of the user
 *
 */
data class StreakInfo (

    /**
     * Amount of days the user has completed at least one of the daily tasks in a row
     */
    val streak: Int,

    /**
     * The personal best the user had for the streak
     */
    val streakRecord: Int,

    /**
     * Amount of days the user has completed every daily tasks in a row
     */
    val goldStreak: Int,

    /**
     * The personal best the user had for the gold streak
     */
    val goldStreakRecord: Int

)