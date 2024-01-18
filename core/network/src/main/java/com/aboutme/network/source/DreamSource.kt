package com.aboutme.network.source

import com.aboutme.core.common.Response
import com.aboutme.network.dto.DreamDto
import com.aboutme.network.dto.update.UpdateDreamDto
import com.aboutme.network.source.base.SyncableDtoAccessor

interface DreamSource : SyncableDtoAccessor<DreamDto, UpdateDreamDto, Long> {

    suspend fun getAll(token: String): Response<List<DreamDto>>

}