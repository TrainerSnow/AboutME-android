package com.aboutme.network.source

import com.aboutme.core.common.Response
import com.aboutme.network.dto.DreamDto
import com.aboutme.network.dto.update.UpdateDreamDto
import com.aboutme.network.source.base.SyncableDtoAccessor
import java.util.UUID

interface DreamSource : SyncableDtoAccessor<DreamDto, UpdateDreamDto, UUID> {

    suspend fun getAll(token: String): Response<List<DreamDto>>

}