package com.aboutme.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboutme.core.model.data.NameInfo

@Entity(
    tableName = "user"
)
data class UserEntity(

    @PrimaryKey
    val email: String,

    val nameInfo: NameInfo

)
