package com.aboutme.network.util

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Query

internal fun <Data : Mutation.Data> ApolloClient.mutationWithToken(
    mutation: Mutation<Data>,
    token: String
) = mutation(mutation)
    .addHttpHeader(
        name = "Authorization",
        value = "Bearer: $token"
    )

fun <Data : Query.Data> ApolloCall<Data>.authentication(token: String) =
    addHttpHeader("Authorization", "Bearer: $token")

internal fun ApolloResponse<*>.hasErrorOrException() = exception != null || errors != null