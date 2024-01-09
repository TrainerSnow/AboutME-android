package com.aboutme.core.model.sort

/**
 * Implemented by models that can be sorted by any of the [SortMode]
 *
 */
interface Sortable<Model> {

    /**
     * Returns a comparator for the given [SortMode]
     */
    fun comparatorFor(mode: SortMode): Comparator<Model>?

}