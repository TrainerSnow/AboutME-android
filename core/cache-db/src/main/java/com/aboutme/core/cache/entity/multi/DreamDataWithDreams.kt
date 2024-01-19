package com.aboutme.core.cache.entity.multi

import androidx.room.Embedded
import androidx.room.Relation
import com.aboutme.core.cache.entity.DreamEntity
import com.aboutme.core.cache.entity.daily.DreamDataEntity

data class DreamDataWithDreams(

    @Embedded val dreamData: DreamDataEntity,

    @Relation(
        parentColumn = "date",
        entityColumn = "date"
    )
    val dreams: Set<DreamEntity>

)
