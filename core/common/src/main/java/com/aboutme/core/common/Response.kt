package com.aboutme.core.common

/**
 * Response states from any kind of request, e.g. database, network or repository.
 *
 * @param Data The data type
 */
sealed class Response<Data> {

    /**
     * The request was completed successfully
     *
     * @param Data The data type
     * @property data The resolved data
     */
    data class Success<Data>(
        val data: Data
    ) : Response<Data>()

    /**
     * The response is currently loading
     *
     * @param Data The data type. Needed for type checking.
     */
    class Loading<Data> : Response<Data>()

    /**
     * The request could not be satisfied as errors were thrown
     *
     * @param Data The data type. Needed for type checking.
     * @property errors The errors that prevented data from being fetched.
     */
    data class Error<Data>(
        val errors: Set<ResponseError>
    ) : Response<Data>()

    /**
     * Maps the response to another type
     *
     * @param Aim The data typed to aim for
     * @param mapper The conversion function
     * @return A new response, with the same state, and converted data
     */
    fun <Aim> map(mapper: (Data) -> Aim): Response<Aim> = when (this) {
        is Error -> Error(errors)
        is Loading -> Loading()
        is Success -> Success(mapper(data))
    }

}
