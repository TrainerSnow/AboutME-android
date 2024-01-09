package com.snow.core.input.data

import com.snow.core.input.error.InputError

/**
 * The input of a text field
 */
data class TextInput(

    /**
     * The string input from the user
     */
    val input: String = "",

    /**
     * The resolved error, or null
     */
    val error: InputError? = null

) {

    /**
     * Whether the input has an error
     */
    fun isError(): Boolean = error != null

    fun withoutError(): TextInput = TextInput(input, null)

}
