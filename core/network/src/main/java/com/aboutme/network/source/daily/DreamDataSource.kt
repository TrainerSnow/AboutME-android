package com.aboutme.network.source.daily;

import com.aboutme.network.dto.daily.DreamDataDto
import com.aboutme.network.source.base.DailyDataSource
import com.aboutme.network.source.base.SyncableDtoAccessor
import java.time.LocalDate

interface DreamDataSource: DailyDataSource<DreamDataDto>