package com.aboutme.network.dto.base

import java.time.Instant
import java.util.UUID

interface SyncableDto: UpdateDto {

    val createdAt: Instant

    val remoteId: UUID

}