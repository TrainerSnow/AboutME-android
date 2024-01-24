package com.aboutme.network.implementation

import com.aboutme.DeleteUserMutation
import com.aboutme.LogInMutation
import com.aboutme.LogOutAllMutation
import com.aboutme.LogOutMutation
import com.aboutme.RefreshMutation
import com.aboutme.SignUpMutation
import com.aboutme.UpdateUserMutation
import com.aboutme.core.common.Response
import com.aboutme.network.dto.NameInfoDto
import com.aboutme.network.dto.UserDto
import com.aboutme.network.dto.update.UpdateUserDto
import com.aboutme.network.mapping.mapResponse
import com.aboutme.network.mapping.toAuthUser
import com.aboutme.network.mapping.toUserData
import com.aboutme.network.source.UserNetworkSource
import com.aboutme.network.util.authentication
import com.aboutme.type.NameInfoInput
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional

internal class ApolloUserNetworkSource(

    private val client: ApolloClient

) : UserNetworkSource {

    override suspend fun signUp(
        email: String,
        password: String,
        nameInfo: NameInfoDto
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
        .mapResponse { it.signUp?.authUserFragment?.toAuthUser() }

    override suspend fun logIn(email: String, password: String) = client
        .mutation(
            LogInMutation(
                email = email,
                password = password
            )
        ).execute()
        .mapResponse { it.login?.authUserFragment?.toAuthUser() }

    override suspend fun logOut(
        refreshToken: String,
        token: String
    ) = client
        .mutation(LogOutMutation(refreshToken))
        .authentication(token)
        .execute()
        .mapResponse { it.logout?.userFragment?.toUserData() }

    override suspend fun logOutAll(token: String) = client
        .mutation(mutation = LogOutAllMutation())
        .authentication(token)
        .execute()
        .mapResponse { it.logoutAll?.userFragment?.toUserData() }

    override suspend fun refresh(refreshToken: String) = client
        .mutation(
            RefreshMutation(refreshToken)
        ).execute()
        .mapResponse {
            it.refresh?.authUserFragment?.toAuthUser()
        }

    override suspend fun deleteUser(token: String) = client
        .mutation(DeleteUserMutation())
        .authentication(token)
        .execute()
        .mapResponse {
            it.deleteUser?.userFragment?.toUserData()
        }

    override suspend fun delete(id: String, token: String) {
        throw NotImplementedError("Deleting a user via the Syncable API is not supported!")
    }

    override suspend fun update(id: String, dto: UpdateUserDto, token: String) {
        client
            .mutation(UpdateUserMutation(dto.toUpdateInput()))
            .authentication(token)
            .execute()
    }

    override suspend fun insert(id: String, dto: UserDto, token: String): Response<String> {
        throw NotImplementedError("Inserting a user via the Syncable API is not supported!")
    }


}