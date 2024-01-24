package com.aboutme.network.dto

import com.aboutme.network.dto.base.SyncableDto
import java.time.Instant
import java.util.UUID

data class UserDto(

    val email: String,

    val nameInfo: NameInfoDto,

    override val createdAt: Instant,

    override val updatedAt: Instant,

    override val remoteId: UUID

): SyncableDto
