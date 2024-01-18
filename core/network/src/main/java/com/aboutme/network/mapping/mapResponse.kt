package com.aboutme.network.mapping;

import com.aboutme.core.common.Response
import com.aboutme.core.common.ResponseError
import com.aboutme.network.util.hasErrorOrException
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Operation

internal fun <Data : Operation.Data, Aim> ApolloResponse<Data>.mapResponse(mapper: (Data) -> Aim?): Response<Aim> {
    println("Mapping response $this with data = ${this.data} and exception = ${this.exception?.cause} and errors  = ${this.errors?.joinToString()}")
    //TODO: This logic is bad. It enforces all return types from graphql to be not nullable
    return if (hasErrorOrException()) mapError()
    else mapSuccess(mapper)
}

private fun <Data : Operation.Data, Aim> ApolloResponse<Data>.mapSuccess(mapper: (Data) -> Aim?): Response<Aim> =
    Response.Success(mapper(dataAssertNoErrors)!!)


private fun <Data : Operation.Data, Aim> ApolloResponse<Data>.mapError(): Response<Aim> =
    Response.Error(toModelErrors())

//TODO: This is bad. Error throwing should also be unified on the backend
private fun ApolloResponse<*>.toModelErrors(): Set<ResponseError> = when {
    exception != null -> listOf(ResponseError.Network).toSet() //Exception should only occur when network issue
    errors != null -> errors!!.map(Error::toModelError).toSet()
    else -> emptySet()
}

private fun Error.toModelError(): ResponseError =
    when (message) {
        "Unauthorized" -> ResponseError.NotAuthorized
        "NetworkError when attempting to fetch resource." -> ResponseError.Network
        else -> when (extensions?.get("code")) {
            401 -> ResponseError.NotAuthorized
            404 -> ResponseError.NotFound
            409 -> ResponseError.Conflict
            else -> ResponseError.Unknown
        }
    }