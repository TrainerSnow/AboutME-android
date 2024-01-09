package com.aboutme.core.model.daily.data

import com.aboutme.core.model.sort.SortMode
import com.aboutme.core.model.sort.Sortable

/**
 * A data for one day that stores the mood of the user
 */
sealed class MoodData(

    /**
     * The average mood over the day
     */
    val averageMood: Float

) : Sortable<MoodData> {

    /**
     * When the mood was constant over the day
     */
    data class ConstantMoodData(

        /**
         * The constant mood over the day
         */
        val mood: Float

    ) : MoodData(mood)

    /**
     * When the mood varied over the day
     */
    data class VaryingMoodData(

        /**
         * Mood in the beginning of the day
         */
        val morning: Float,

        /**
         * Mood in the middle of the day
         */
        val noon: Float,

        /**
         * Mood in the end of the day
         */
        val evening: Float

    ) : MoodData((morning + noon + evening) / 3F)

    override fun comparatorFor(mode: SortMode): Comparator<MoodData>? = when (mode) {
        SortMode.Amount -> compareBy { it.averageMood }
        else -> null
    }

}
