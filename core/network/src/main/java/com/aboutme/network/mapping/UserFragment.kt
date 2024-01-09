package com.aboutme.network.mapping

import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.fragment.UserFragment

fun UserFragment.toUserData(): UserData =
    UserData(
        nameInfo = nameInfo.run {
            NameInfo(firstName, middleName, lastName, title)
        },
        email = email,
        createdAt = created,
        updatedAt = updated
    )