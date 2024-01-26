package com.aboutme.core.data.repository

import com.aboutme.core.model.daily.DailyData
import com.aboutme.core.model.daily.DailyDataProgress
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Repository giving access to the daily data for each day
 *
 */
interface DailyDataRepository {

    /**
     * Gets the daily data for the given [date]
     */
    fun getForDay(date: LocalDate): Flow<DailyData>

    /**
     * Gets the progress of data for the given [date]
     */
    fun getProgress(date: LocalDate): Flow<DailyDataProgress>

}