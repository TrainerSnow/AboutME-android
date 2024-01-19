package com.aboutme.network.source.daily;

import com.aboutme.network.dto.daily.DiaryDataDto
import com.aboutme.network.source.base.DailyDataSource
import com.aboutme.network.source.base.SyncableDtoAccessor
import java.time.LocalDate

interface DiaryDataSource: DailyDataSource<DiaryDataDto>