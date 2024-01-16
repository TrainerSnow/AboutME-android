package com.aboutme.core.ui.model

import androidx.annotation.StringRes
import com.aboutme.core.common.ResponseError
import com.aboutme.core.ui.R

/**
 * Gets the resource id for the Response error
 */
val ResponseError.description: Int
    @StringRes get() = when (this) {
        ResponseError.Conflict -> R.string.response_error_conflict
        ResponseError.Network -> R.string.response_error_network
        ResponseError.NotAuthorized -> R.string.response_error_unauthorized
        ResponseError.NotFound -> R.string.response_error_notfound
        ResponseError.Unknown -> R.string.response_error_unknown
    }