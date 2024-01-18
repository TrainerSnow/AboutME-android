package com.aboutme.network.dto.base

import java.time.Instant

interface SyncableDto: UpdateDto {

    val createdAt: Instant

}