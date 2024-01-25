package com.aboutme.core.model.daily

data class DailyDataProgress(

    /**
     * The amount of completed tasks
     */
    val completed: Int,

    /**
     * The total amount of tasks to complete
     */
    val total: Int

) {

    init {
        require(completed >= 0)
        require(total >= 0)
        require(completed <= total)
    }

    fun getPercentage() = completed.toFloat() / total.toFloat()

}
