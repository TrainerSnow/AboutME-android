package com.aboutme.network.implementation

import com.aboutme.DeleteUserMutation
import com.aboutme.LogInMutation
import com.aboutme.LogOutAllMutation
import com.aboutme.LogOutMutation
import com.aboutme.RefreshMutation
import com.aboutme.SignUpMutation
import com.aboutme.UpdateUserMutation
import com.aboutme.core.model.data.NameInfo
import com.aboutme.network.mapping.mapResponse
import com.aboutme.network.mapping.toAuthUser
import com.aboutme.network.mapping.toUserData
import com.aboutme.network.source.UserNetworkSource
import com.aboutme.network.util.mutationWithToken
import com.aboutme.type.NameInfoInput
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional

internal class ApolloUserNetworkSource(

    private val client: ApolloClient

) : UserNetworkSource {

    override suspend fun signUp(
        email: String,
        password: String,
        nameInfo: NameInfo
    ) = client
        .mutation(
            SignUpMutation(
                email = email,
                password = password,
                nameInfo = NameInfoInput(
                    firstName = nameInfo.firstName,
                    middleName = Optional.presentIfNotNull(nameInfo.middleName),
                    lastName = Optional.presentIfNotNull(nameInfo.lastName),
                    title = Optional.presentIfNotNull(nameInfo.title)
                )
            )
        ).execute()
        .mapResponse { it.signUp.authUserFragment.toAuthUser() }

    override suspend fun logIn(email: String, password: String) = client
        .mutation(
            LogInMutation(
                email = email,
                password = password
            )
        ).execute()
        .mapResponse { it.login.authUserFragment.toAuthUser() }

    override suspend fun logOut(
        refreshToken: String,
        token: String
    ) = client
        .mutationWithToken(
            mutation = LogOutMutation(refreshToken),
            token = token
        ).execute()
        .mapResponse { it.logout.userFragment.toUserData() }

    override suspend fun logOutAll(token: String) = client
        .mutationWithToken(
            mutation = LogOutAllMutation(),
            token = token
        ).execute()
        .mapResponse { it.logoutAll.userFragment.toUserData() }

    override suspend fun refresh(refreshToken: String) = client
        .mutation(
            RefreshMutation(refreshToken)
        ).execute()
        .mapResponse {
            it.refresh.authUserFragment.toAuthUser()
        }

    override suspend fun deleteUser(token: String) = client
        .mutationWithToken(
            mutation = DeleteUserMutation(),
            token = token
        ).execute()
        .mapResponse {
            it.deleteUser.userFragment.toUserData()
        }

    override suspend fun updateUser(nameInfo: NameInfo, token: String) = client
        .mutationWithToken(
            mutation = UpdateUserMutation(
                nameInfo = NameInfoInput(
                    firstName = nameInfo.firstName,
                    middleName = Optional.presentIfNotNull(nameInfo.middleName),
                    lastName = Optional.presentIfNotNull(nameInfo.lastName),
                    title = Optional.presentIfNotNull(nameInfo.title)
                )
            ),
            token = token
        ).execute()
        .mapResponse {
            it.updateUser.userFragment.toUserData()
        }
}