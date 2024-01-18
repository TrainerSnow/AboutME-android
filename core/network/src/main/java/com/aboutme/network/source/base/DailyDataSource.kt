package com.aboutme.network.source.base

import com.aboutme.core.common.Response
import com.aboutme.network.dto.base.SyncableDto
import java.time.LocalDate

interface DailyDataSource<Dto : SyncableDto> : SyncableDtoAccessor<Dto, Dto, LocalDate> {

    suspend fun getAll(token: String): Response<List<Dto>>

    suspend fun getByDate(date: LocalDate, token: String): Response<Dto>

}