package com.aboutme.core.model.sort

/**
 * Different possible ways to sort models
 */
enum class SortMode {

    /**
     * Sorts by the name alphabetically
     *
     */
    Name,

    /**
     * Sorts by amount
     *
     */
    Amount,

    /**
     * Sorts by whether the item is completed or not
     */
    Completed,

    /**
     * Sorts by the associated date
     *
     */
    Date,

    Created,

    Updates,

    Length,

    Category,

    None

}
