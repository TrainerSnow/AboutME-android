package com.aboutme.core.data.implementation;

import com.aboutme.core.data.AuthService
import com.aboutme.core.data.repository.DailyDataRepository
import com.aboutme.network.source.DailyDataSource
import java.time.LocalDate

internal class NetworkDailyDataRepository(
    private val dailyDataSource: DailyDataSource,
    private val authService: AuthService
) : DailyDataRepository {

    override suspend fun getForDay(date: LocalDate) = authService.saveAuthTransaction { token ->
        dailyDataSource.getByDate(date, token)
    }

}