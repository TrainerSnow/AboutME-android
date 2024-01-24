package com.aboutme.network.source.base

import com.aboutme.core.common.Response
import com.aboutme.network.dto.base.SyncableDto

interface SyncableDtoAccessor<
        CreateDto : SyncableDto,
        UpdateDto : com.aboutme.network.dto.base.UpdateDto,
        Identifier
        > {

    suspend fun delete(id: Identifier, token: String)

    suspend fun update(id: Identifier, dto: UpdateDto, token: String)

    suspend fun insert(id: Identifier, dto: CreateDto, token: String): Response<CreateDto>

}