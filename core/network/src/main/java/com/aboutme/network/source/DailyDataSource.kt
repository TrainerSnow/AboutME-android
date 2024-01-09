package com.aboutme.network.source

import com.aboutme.core.model.Response
import com.aboutme.core.model.daily.DailyData
import java.time.LocalDate

/**
 * Network source which grants access to the daily datas, bundling all the datas for one day
 *
 */
interface DailyDataSource {

    /**
     * Gets the [DailyData] for one day.
     */
    suspend fun getByDate(date: LocalDate, token: String): Response<DailyData>

}