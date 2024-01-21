package com.aboutme.core.model.preferences

import kotlin.time.Duration

data class SyncPreferences(

    val onlyWifi: Boolean,

    val period: Duration,

    val syncOption: SyncOption

)
