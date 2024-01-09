package com.snow.core.input

import android.util.Patterns
import com.snow.core.input.data.TextInput
import com.snow.core.input.error.InputError

private object Config {

    const val passwordLength = 8

}

/**
 * Creates a [TextInput] for an email input
 */
fun String.createForEmail(): TextInput {
    val isEmail = Patterns.EMAIL_ADDRESS.matcher(this).matches()

    return TextInput(
        this,
        if (!isEmail) InputError.BadEmail
        else null
    )
}


/**
 * Creates a [TextInput] for a password input
 */
fun String.createForPassword(): TextInput {
    val isLong = length >= Config.passwordLength
    val isContent = any { it.isDigit() } && any { it.isUpperCase() } && any { it.isLowerCase() }

    return TextInput(
        this,
        if (!isLong) InputError.PasswordLength
        else if (!isContent) InputError.PasswordContent
        else null
    )
}


/**
 * Creates a [TextInput] for a name input
 */
fun String.createForName(): TextInput {
    val isNotEmpty = isNotBlank()

    return TextInput(
        this,
        if (!isNotEmpty) InputError.NameEmpty
        else null
    )
}