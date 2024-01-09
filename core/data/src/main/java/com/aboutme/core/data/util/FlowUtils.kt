package com.aboutme.core.data.util

import com.aboutme.core.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> responseFlow(
    provider: suspend () -> Response<T>
): Flow<Response<T>> = flow {
    emit(Response.Loading())
    emit(provider())
}