package com.aboutme.network.implementation.daily;

import com.aboutme.AddOrUpdateDreamDataMutation
import com.aboutme.DeleteDiaryDataMutation
import com.aboutme.GetAllDreamDatasQuery
import com.aboutme.GetDreamDataByDateQuery
import com.aboutme.core.common.Response
import com.aboutme.network.dto.daily.DreamDataDto
import com.aboutme.network.mapping.mapResponse
import com.aboutme.network.mapping.toDreamData
import com.aboutme.network.source.daily.DreamDataSource
import com.aboutme.network.util.authentication
import com.apollographql.apollo3.ApolloClient
import java.time.LocalDate

class ApolloDreamDataSource(
    private val client: ApolloClient
) : DreamDataSource {
    override suspend fun getAll(token: String): Response<List<DreamDataDto>> = client
        .query(GetAllDreamDatasQuery())
        .authentication(token)
        .execute()
        .mapResponse { it.getAllDreamDatas?.map { it.dreamDataFragment.toDreamData() } }

    override suspend fun getByDate(date: LocalDate, token: String): Response<DreamDataDto> = client
        .query(GetDreamDataByDateQuery(date))
        .authentication(token)
        .execute()
        .mapResponse { it.getDreamData?.dreamDataFragment?.toDreamData() }

    override suspend fun delete(id: LocalDate, token: String) {
        client
            .mutation(DeleteDiaryDataMutation(id))
            .authentication(token)
            .execute()
    }

    override suspend fun update(id: LocalDate, dto: DreamDataDto, token: String) {
        insert(id, dto, token)
    }

    override suspend fun insert(id: LocalDate, dto: DreamDataDto, token: String) = client
        .mutation(AddOrUpdateDreamDataMutation(dto.toInput()))
        .authentication(token)
        .execute()
        .mapResponse { it.addDreamData.dreamDataFragment.date }


}