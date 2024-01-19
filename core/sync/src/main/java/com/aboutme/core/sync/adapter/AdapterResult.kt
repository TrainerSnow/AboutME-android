package com.aboutme.core.sync.adapter

/**
 * Result of a sync action for an adapter
 */
enum class AdapterResult {

    /**
     * Updated the servers value with the locally saved one
     */
    UpdatedServer,

    /**
     * Updated the local value with the servers one
     */
    UpdatedLocal,

    /**
     * Uploaded a local value to the server
     */
    AddedServer,

    /**
     * Downloaded a server value to the local
     */
    AddedLocal,

    /**
     * Deleted a value on the server
     */
    DeletedServer,

    /**
     * Deleted a local value
     */
    DeletedLocal,

    /**
     * No syncing action was done
     */
    None

}