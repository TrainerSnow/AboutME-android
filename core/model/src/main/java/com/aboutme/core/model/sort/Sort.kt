package com.aboutme.core.model.sort

@Suppress("UNCHECKED_CAST")
fun <Model> List<Model>.sortBy(sort: SortConfig): List<Model> =
    (first() as? Sortable<Model>)?.let {
        it.comparatorFor(sort.mode)
            ?.let(this::sortedWith)
            ?.let {
                if (sort.direction == SortDirection.Descending) it.reversed() else it
            }
            ?: this
    } ?: this
