package com.aboutme.core.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.model.data.NameInfo
import java.time.Instant

@Entity(
    tableName = "user"
)
data class UserEntity(

    @PrimaryKey
    val email: String,

    val nameInfo: NameInfo,

    val createdAt: Instant,

    val updatedAt: Instant

)
