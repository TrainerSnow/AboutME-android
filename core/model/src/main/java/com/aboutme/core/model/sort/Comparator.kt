package com.aboutme.core.model.sort

fun <T> emptyComparator(): Comparator<T> = Comparator { _, _ -> 0 }