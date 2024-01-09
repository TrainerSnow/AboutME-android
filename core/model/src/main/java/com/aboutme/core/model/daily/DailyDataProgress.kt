package com.aboutme.core.model.daily

/**
 * How much a daily data has been finished by the user
 */
sealed class DailyDataProgress {

    /**
     * The the data can either be finished or unfinished
     */
    data class BooleanDataProgress(

        /**
         * Whether the data is finished
         */
        val finished: Boolean

    ) : DailyDataProgress()

    /**
     * When the data has several tasks that can be finished
     */
    data class TasksDataProgress(

        /**
         * The amount of completed tasks
         */
        val completed: Int,

        /**
         * The total amount of tasks to be completed
         */
        val total: Int
    ) : DailyDataProgress()

    /**
     * When the data can be finished by a fraction
     */
    data class PercentDataProgress(

        /**
         * The percent of how much the data is finished
         */
        val percent: Int

    ) : DailyDataProgress()

    /**
     * Whether the data if finished
     */
    val isFinished: Boolean
        get() = when (this) {
            is BooleanDataProgress -> finished
            is PercentDataProgress -> percent == 100
            is TasksDataProgress -> completed >= total
        }

}
