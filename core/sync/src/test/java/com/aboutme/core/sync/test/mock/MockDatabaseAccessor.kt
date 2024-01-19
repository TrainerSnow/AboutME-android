package com.aboutme.core.sync.test.mock;

import com.aboutme.core.cache.dao.base.SyncableEntityAccessor

internal class MockDatabaseAccessor :
    SyncableEntityAccessor<TestEntity> {

    private val entities = mutableListOf<TestEntity>()

    override suspend fun update(entity: TestEntity) {
        if (!entities.removeIf { it.id == entity.id }) throw IllegalStateException("")
        entities.add(entity)
    }

    override suspend fun delete(entity: TestEntity): Int {
        entities.removeIf { it.id == entity.id }
        return -1 // Not sure what to return here
    }

    override suspend fun insert(entity: TestEntity) {
        if(entities.any { it.id == entity.id }) throw IllegalStateException("Identifier already exists!")
        entities.add(entity)
    }

    fun reset() {
        entities.clear()
    }

    fun getAll() = entities

    fun first() = entities.first()

}