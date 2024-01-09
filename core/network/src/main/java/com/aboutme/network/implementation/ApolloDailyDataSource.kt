package com.aboutme.network.implementation;

import com.aboutme.AllDayDatasQuery
import com.aboutme.DayDataForDateQuery
import com.aboutme.core.model.Response
import com.aboutme.core.model.daily.DailyData
import com.aboutme.network.mapping.mapResponse
import com.aboutme.network.mapping.toDailyData
import com.aboutme.network.source.DailyDataSource
import com.aboutme.network.util.authentication
import com.apollographql.apollo3.ApolloClient
import java.time.LocalDate

internal class ApolloDailyDataSource(
    private val client: ApolloClient
) : DailyDataSource {

    override suspend fun getByDate(date: LocalDate, token: String): Response<DailyData> =
        client
            .query(DayDataForDateQuery(date))
            .authentication(token)
            .execute()
            .mapResponse { it.dayData.dayDataFragment.toDailyData() }

    override suspend fun getAll(token: String): Response<List<DailyData>> =
        client
            .query(AllDayDatasQuery())
            .authentication(token)
            .execute()
            .mapResponse { it.dayDatas.map { it.dayDataFragment.toDailyData() } }

}