package com.aboutme.core.cache.dao.base

import com.aboutme.core.cache.entity.base.SyncableEntity

interface SyncableEntityAccessor<Entity: SyncableEntity> {

    suspend fun update(entity: Entity)

    suspend fun delete(entity: Entity): Int

    suspend fun insert(entity: Entity)

}