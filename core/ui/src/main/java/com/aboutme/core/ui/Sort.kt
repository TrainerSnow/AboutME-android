package com.aboutme.core.ui

import androidx.annotation.StringRes
import com.aboutme.core.model.sort.SortDirection
import com.aboutme.core.model.sort.SortMode

val SortMode.label: Int
    @StringRes get() = when (this) {
        SortMode.Name -> R.string.sortmode_name
        SortMode.Amount -> R.string.sortmode_amount
        SortMode.Completed -> R.string.sortmode_completed
        SortMode.Date -> R.string.sortmode_date
        SortMode.Created -> R.string.sortmode_created
        SortMode.Updates -> R.string.sortmode_updated
        SortMode.Length -> R.string.sortmode_length
        SortMode.Category -> R.string.sortmode_category
        SortMode.None -> R.string.sortmode_none
    }

val SortDirection.label: Int
    @StringRes get() = when (this) {
        SortDirection.Ascending -> R.string.sortdirection_ascending
        SortDirection.Descending -> R.string.sortdirection_descending
        SortDirection.None -> R.string.sortdirection_none
    }