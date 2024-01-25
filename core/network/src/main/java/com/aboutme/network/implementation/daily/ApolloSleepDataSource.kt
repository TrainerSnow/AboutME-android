package com.aboutme.network.implementation.daily

import com.aboutme.AddOrUpdateSleepDataMutation
import com.aboutme.DeleteSleepDataMutation
import com.aboutme.GetAllSleepDatasQuery
import com.aboutme.GetSleepDataByDateQuery
import com.aboutme.core.common.Response
import com.aboutme.network.dto.daily.SleepDataDto
import com.aboutme.network.mapping.mapResponse
import com.aboutme.network.mapping.toSleepData
import com.aboutme.network.source.daily.SleepDataSource
import com.aboutme.network.util.authentication
import com.apollographql.apollo3.ApolloClient
import java.time.LocalDate

class ApolloSleepDataSource(
    private val client: ApolloClient
) : SleepDataSource {
    override suspend fun getAll(token: String): Response<List<SleepDataDto>> = client
        .query(GetAllSleepDatasQuery())
        .authentication(token)
        .execute()
        .mapResponse { it.getAllSleepDatas.map { it.sleepDataFragment.toSleepData() } }

    override suspend fun getByDate(date: LocalDate, token: String): Response<SleepDataDto> = client
        .query(GetSleepDataByDateQuery(date))
        .authentication(token)
        .execute()
        .mapResponse { it.getSleepDataByDate.sleepDataFragment.toSleepData() }

    override suspend fun delete(id: LocalDate, token: String) {
        client
            .mutation(DeleteSleepDataMutation(id))
            .authentication(token)
            .execute()
    }

    override suspend fun update(id: LocalDate, dto: SleepDataDto, token: String) {
        insert(id, dto, token)
    }

    override suspend fun insert(id: LocalDate, dto: SleepDataDto, token: String) = client
        .mutation(AddOrUpdateSleepDataMutation(dto.toInput()))
        .authentication(token)
        .execute()
        .mapResponse { it.addSleepData.sleepDataFragment.toSleepData() }


}