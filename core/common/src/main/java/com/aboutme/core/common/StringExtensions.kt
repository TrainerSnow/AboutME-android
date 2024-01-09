package com.aboutme.core.common

/**
 * Gets the string, or null if it is blank
 */
fun String.takeAsInput() = ifBlank { null }