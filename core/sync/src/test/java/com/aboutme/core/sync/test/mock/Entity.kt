package com.aboutme.core.sync.test.mock

import com.aboutme.core.database.entity.base.SyncableEntity
import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.network.dto.base.UpdateDto
import java.time.Instant


internal data class TestEntity(

    val id: Long,

    val content: String,

    override val createdAt: Instant,

    override val deletedAt: Instant?,

    override val updatedAt: Instant


) : SyncableEntity

internal data class TestDto(

    val id: Long,

    val content: String,

    override val createdAt: Instant,

    override val updatedAt: Instant

) : SyncableDto

internal data class UpdateTestDto(

    val content: String,

    override val updatedAt: Instant

) : UpdateDto