package com.aboutme.network.dto

import com.aboutme.type.NameInfoInput
import com.apollographql.apollo3.api.Optional

data class NameInfoDto(

    val firstName: String,

    val middleName: String? = null,

    val lastName: String? = null,

    val title: String? = null

) {

    fun toInput() = NameInfoInput(
        firstName = firstName,
        middleName = Optional.presentIfNotNull(middleName),
        lastName = Optional.presentIfNotNull(lastName),
        title = Optional.presentIfNotNull(title)
    )

}
