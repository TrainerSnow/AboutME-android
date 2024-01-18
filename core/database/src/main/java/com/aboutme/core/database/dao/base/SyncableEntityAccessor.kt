package com.aboutme.core.database.dao.base

import com.aboutme.core.database.entity.base.SyncableEntity

interface SyncableEntityAccessor<Entity: SyncableEntity> {

    suspend fun update(entity: Entity)

    suspend fun delete(entity: Entity): Int

    suspend fun insert(entity: Entity)

}