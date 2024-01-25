package com.aboutme.core.model.base

import java.util.UUID

interface IdentifiableModel {

    val localId: Long?

    val remoteId: UUID?

}