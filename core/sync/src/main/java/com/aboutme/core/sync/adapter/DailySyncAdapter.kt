package com.aboutme.core.sync.adapter;

import com.aboutme.core.cache.dao.base.SyncableEntityAccessor
import com.aboutme.core.cache.entity.base.SyncableEntity
import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.network.source.base.SyncableDtoAccessor
import java.time.LocalDate

internal abstract class DailySyncAdapter<
        DbEntity : SyncableEntity, NetworkDto : SyncableDto>(
    dbAccessor: SyncableEntityAccessor<DbEntity>,

    networkAccessor: SyncableDtoAccessor<NetworkDto, NetworkDto, LocalDate>,

    networkCall: suspend (suspend (String) -> Unit) -> Unit
) :
    SyncAdapter<DbEntity, NetworkDto, NetworkDto, LocalDate>(
        dbAccessor,
        networkAccessor,
        networkCall
    ) {

    override fun convertEntityToUpdateDto(entity: DbEntity): NetworkDto {
        return convertEntityToDto(entity)
    }

}