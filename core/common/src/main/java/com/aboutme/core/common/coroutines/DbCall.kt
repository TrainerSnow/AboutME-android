package com.aboutme.core.common.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <R> dbCall(block: suspend () -> R) = withContext(Dispatchers.IO) {
    block()
}