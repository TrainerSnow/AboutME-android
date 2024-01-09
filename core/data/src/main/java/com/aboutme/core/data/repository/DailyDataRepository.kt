package com.aboutme.core.data.repository

import com.aboutme.core.model.Response
import com.aboutme.core.model.daily.DailyData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Repository giving access to the daily data for each day
 *
 */
interface DailyDataRepository {

    fun getForDay(date: LocalDate): Flow<DailyData>

}