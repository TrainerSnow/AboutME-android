package com.aboutme.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "dream"
)
data class DreamEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    //Foreign key to [DreamData]
    val date: LocalDate,

    val content: String,

    val annotation: String? = null,

    val mood: Float? = null,

    val clearness: Float? = null

)
