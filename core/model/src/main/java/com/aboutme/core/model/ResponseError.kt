package com.aboutme.core.model;

/**
 * Errors that can occur when fetching a [Response]
 */
sealed class ResponseError {

    /**
     * The cause for the error is unknowm
     */
    data object Unknown: ResponseError()

    /**
     * The requested resource was not found
     */
    data object NotFound: ResponseError()

    /**
     * A problem related to the network occurred
     */
    data object Network: ResponseError()

    /**
     * Request was not authorized
     */
    data object NotAuthorized: ResponseError()

    /**
     * The request conflicted with internal state
     */
    data object Conflict: ResponseError()

}