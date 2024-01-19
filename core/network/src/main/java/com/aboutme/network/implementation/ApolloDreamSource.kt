package com.aboutme.network.implementation;

import com.aboutme.AddDreamMutation
import com.aboutme.CreateDreamMutation
import com.aboutme.DeleteDreamMutation
import com.aboutme.GetAllDreamsQuery
import com.aboutme.network.dto.DreamDto
import com.aboutme.network.dto.update.UpdateDreamDto
import com.aboutme.network.mapping.mapResponse
import com.aboutme.network.mapping.toDream
import com.aboutme.network.source.DreamSource
import com.aboutme.network.util.authentication
import com.apollographql.apollo3.ApolloClient

internal class ApolloDreamSource(
    private val client: ApolloClient
) : DreamSource {

    override suspend fun delete(id: Long, token: String) {
        client
            .mutation(DeleteDreamMutation(id))
            .authentication(token)
            .execute()
    }

    override suspend fun update(id: Long, dto: UpdateDreamDto, token: String) {
        client
            .mutation(CreateDreamMutation(dto.toUpdateInput(), id))
            .authentication(token)
            .execute()
    }

    override suspend fun insert(id: Long, dto: DreamDto, token: String) {
        client
            .mutation(AddDreamMutation(dto.toAddInput()))
    }

    override suspend fun getAll(token: String) = client
        .query(GetAllDreamsQuery())
        .authentication(token)
        .execute()
        .mapResponse { it.getAllDreams?.map { it.dreamFragment.toDream() } }

}