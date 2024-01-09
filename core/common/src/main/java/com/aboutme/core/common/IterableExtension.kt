package com.aboutme.core.common

operator fun <T> Set<T>?.contains(other: T) = if (this == null) false else other in this