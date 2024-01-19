package com.aboutme.core.sync.result;

/**
 * Possible outcomes that the sync action might have
 */
sealed interface SyncResult {

    /**
     * A synchronization cannot be performed when the user is not authorized.
     * The cause for this is usually an expired refresh token.
     * The user should be prompted to re-login.
     */
    data object NotAuthorized: SyncResult

    /**
     * A synchronization could not be started because the device is not connected to the internet
     */
    data object NoConnection: SyncResult

    /**
     * The synchronization was successful
     */
    data object Success: SyncResult

}