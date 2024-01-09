package com.aboutme.core.model.sort

/**
 * Configuration to sort a list of items
 *
 */
data class SortConfig(

    /**
     * The direction to order by
     */
    val direction: SortDirection,

    /**
     * The mode to order by
     */
    val mode: SortMode

) {

    companion object {

        val None = SortConfig(SortDirection.None, SortMode.None)

    }

}
