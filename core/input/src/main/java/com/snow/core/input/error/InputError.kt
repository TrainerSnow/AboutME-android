package com.snow.core.input.error

import androidx.annotation.StringRes
import com.snow.core.input.R

/**
 * Bundles errors that can occur when a users enters values into a text field
 */
enum class InputError(
    @StringRes val description: Int
) {

    /**
     * Input is supposed to be a valid email, but wasn't
     */
    BadEmail(R.string.error_bad_email),

    /**
     * The email has already been registered
     */
    EmailTaken(R.string.error_email_taken),

    /**
     * The password is too short
     */
    PasswordLength(R.string.error_password_length),

    /**
     * The password does not contain the needed characters
     */
    PasswordContent(R.string.error_password_content),

    /**
     * The name is empty
     */
    NameEmpty(R.string.error_name_empty),

    /**
     * The entered value does not belong to any account
     */
    InvalidCredential(R.string.error_invalid_credential),

    /**
     * The entered value does not match the email of the account
     */
    EmailConfirmationNotMatch(R.string.error_email_confirmation_not_match)

}