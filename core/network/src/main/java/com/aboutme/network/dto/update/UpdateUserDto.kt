package com.aboutme.network.dto.update

import com.aboutme.network.dto.NameInfoDto
import com.aboutme.network.dto.base.UpdateDto
import com.aboutme.type.UpdateUserInput
import java.time.Instant

data class UpdateUserDto(

    val nameInfo: NameInfoDto,

    override val updatedAt: Instant
    
): UpdateDto {

    fun toUpdateInput() = UpdateUserInput(
        nameInfo = nameInfo.toInput(),
        updated = updatedAt
    )

}
