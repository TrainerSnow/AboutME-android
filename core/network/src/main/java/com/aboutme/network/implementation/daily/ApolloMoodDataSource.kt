package com.aboutme.network.implementation.daily

import com.aboutme.AddOrUpdateMoodDataMutation
import com.aboutme.DeleteMoodDataMutation
import com.aboutme.GetAllMoodDatasQuery
import com.aboutme.GetMoodDataByDateQuery
import com.aboutme.core.common.Response
import com.aboutme.network.dto.daily.MoodDataDto
import com.aboutme.network.mapping.mapResponse
import com.aboutme.network.mapping.toMoodData
import com.aboutme.network.source.daily.MoodDataSource
import com.aboutme.network.util.authentication
import com.apollographql.apollo3.ApolloClient
import java.time.LocalDate

class ApolloMoodDataSource(
    private val client: ApolloClient
) : MoodDataSource {
    override suspend fun getAll(token: String): Response<List<MoodDataDto>> = client
        .query(GetAllMoodDatasQuery())
        .authentication(token)
        .execute()
        .mapResponse { it.getAllMoodDatas.map { it.moodDataFragment.toMoodData() } }

    override suspend fun getByDate(date: LocalDate, token: String): Response<MoodDataDto> = client
        .query(GetMoodDataByDateQuery(date))
        .authentication(token)
        .execute()
        .mapResponse { it.getMoodData.moodDataFragment.toMoodData() }


    override suspend fun delete(id: LocalDate, token: String) {
        client
            .mutation(DeleteMoodDataMutation(id))
            .authentication(token)
            .execute()
    }

    override suspend fun update(id: LocalDate, dto: MoodDataDto, token: String) {
        insert(id, dto, token)
    }

    override suspend fun insert(id: LocalDate, dto: MoodDataDto, token: String) = client
        .mutation(AddOrUpdateMoodDataMutation(dto.toInput()))
        .authentication(token)
        .execute()
        .mapResponse { it.addMoodData.moodDataFragment.toMoodData() }


}