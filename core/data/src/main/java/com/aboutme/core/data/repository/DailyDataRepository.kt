package com.aboutme.core.data.repository

import com.aboutme.core.model.Response
import com.aboutme.core.model.daily.DailyData
import java.time.LocalDate

/**
 * Repository giving access to the daily data for each day
 *
 */
interface DailyDataRepository {

    suspend fun getForDay(date: LocalDate): Response<DailyData>

}