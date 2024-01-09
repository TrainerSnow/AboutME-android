package com.aboutme.network.mapping

import com.aboutme.core.model.data.AuthData
import com.aboutme.core.model.data.AuthUser
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.fragment.AuthUserFragment

internal fun AuthUserFragment.toAuthUser(): AuthUser = AuthUser(
    user = user.userFragment.toUserData(),
    authData = AuthData(
        refreshToken = authData.authDataFragment.refreshToken,
        token = authData.authDataFragment.token
    )
)