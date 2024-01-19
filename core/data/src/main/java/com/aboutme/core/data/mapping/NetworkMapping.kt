package com.aboutme.core.data.mapping

import com.aboutme.core.model.data.AuthData
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.network.dto.AuthDataDto
import com.aboutme.network.dto.AuthUserDto
import com.aboutme.network.dto.NameInfoDto
import com.aboutme.network.dto.UserDto

internal fun NameInfo.toDto() = NameInfoDto(firstName, middleName, lastName, title)

internal fun AuthUserDto.toModel() = AuthUser(user.toModel(), authData.toModel())

internal fun UserDto.toModel() = UserData(nameInfo.toModel(), email, createdAt, updatedAt)

internal fun NameInfoDto.toModel() = NameInfo(firstName, middleName, lastName, title)

internal fun AuthDataDto.toModel() = AuthData(refreshToken, token)