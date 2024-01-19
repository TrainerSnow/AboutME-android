package com.aboutme.core.model.preferences

import kotlin.time.Duration

sealed interface SyncPreferences {

    /**
     * Don't sync at all
     */
    data object Not : SyncPreferences

    sealed class EnabledSyncPreferences(

        /**
         * Whether sync should only be done when user is connected to wifi
         */

        open val onlyWifi: Boolean
    ) {

        /**
         * Always sync when a change was made
         */
        data class OnChange(
            override val onlyWifi: Boolean
        ) : EnabledSyncPreferences(onlyWifi)

        /**
         * Always sync when the app is entered
         */
        data class OnEnter(
            override val onlyWifi: Boolean
        ) : EnabledSyncPreferences(onlyWifi)

        /**
         * Sync in a periodic manner
         */
        data class Periodically(
            override val onlyWifi: Boolean,
            val period: Duration
        ) : EnabledSyncPreferences(onlyWifi)

    }

}
