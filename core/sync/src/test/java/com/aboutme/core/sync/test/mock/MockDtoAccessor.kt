package com.aboutme.core.sync.test.mock;

import com.aboutme.network.source.base.SyncableDtoAccessor

internal class MockDtoAccessor : SyncableDtoAccessor<TestDto, UpdateTestDto, Long> {

    private val dtos = mutableListOf<TestDto>()

    override suspend fun delete(id: Long, token: String) {
        dtos.removeIf { it.id == id }
    }

    override suspend fun update(id: Long, dto: UpdateTestDto, token: String) {
        val entity = dtos.find { it.id == id } ?: throw IllegalStateException("")
        dtos.remove(entity)
        dtos.add(entity.copy(content = dto.content, updatedAt = dto.updatedAt))
    }

    override suspend fun insert(id: Long, dto: TestDto, token: String) {
        if(dtos.any { it.id == id }) throw IllegalStateException("")
        dtos.add(dto)
    }

    fun reset() {
        dtos.clear()
    }

    fun getAll() = dtos

    fun first() = dtos.first()

}