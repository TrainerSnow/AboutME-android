package com.aboutme.core.model.data

/**
 * Bundles information about the naming preferences
 *
 */
data class NameInfo(

    /**
     * The first name
     */
    val firstName: String,

    /**
     * The optional middle name
     */
    val middleName: String?,

    /**
     * The last name, if given
     */
    val lastName: String?,

    /**
     * The academic title, if existing
     */
    val title: String?

)
