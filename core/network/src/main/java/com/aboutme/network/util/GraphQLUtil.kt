package com.aboutme.network.util

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation

fun <Data : Operation.Data> ApolloCall<Data>.authentication(token: String) =
    addHttpHeader("Authorization", "Bearer: $token")

internal fun ApolloResponse<*>.hasErrorOrException() = exception != null || errors != null