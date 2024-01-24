package com.aboutme.network.implementation.daily

import com.aboutme.AddOrUpdateDiaryDataMutation
import com.aboutme.DeleteDiaryDataMutation
import com.aboutme.GetAllDiaryDatasQuery
import com.aboutme.GetDiaryDataByDateQuery
import com.aboutme.core.common.Response
import com.aboutme.network.dto.daily.DiaryDataDto
import com.aboutme.network.mapping.mapResponse
import com.aboutme.network.mapping.toDiaryData
import com.aboutme.network.source.daily.DiaryDataSource
import com.aboutme.network.util.authentication
import com.apollographql.apollo3.ApolloClient
import java.time.LocalDate

internal class ApolloDiaryDataSource(
    private val client: ApolloClient
) : DiaryDataSource {
    override suspend fun getAll(token: String): Response<List<DiaryDataDto>> = client
        .query(GetAllDiaryDatasQuery())
        .authentication(token)
        .execute()
        .mapResponse { it.getAllDiaryDatas.map { it.diaryDataFragment.toDiaryData() } }

    override suspend fun getByDate(date: LocalDate, token: String): Response<DiaryDataDto> = client
        .query(GetDiaryDataByDateQuery(date))
        .authentication(token)
        .execute()
        .mapResponse { it.getDiaryDataByDate.diaryDataFragment.toDiaryData() }

    override suspend fun delete(id: LocalDate, token: String) {
        client
            .mutation(DeleteDiaryDataMutation(id))
            .authentication(token)
            .execute()
    }

    override suspend fun update(id: LocalDate, dto: DiaryDataDto, token: String) {
        insert(id, dto, token)
    }

    override suspend fun insert(id: LocalDate, dto: DiaryDataDto, token: String) = client
        .mutation(AddOrUpdateDiaryDataMutation(dto.toInput()))
        .authentication(token)
        .execute()
        .mapResponse { it.addDiaryData.diaryDataFragment.toDiaryData() }


}