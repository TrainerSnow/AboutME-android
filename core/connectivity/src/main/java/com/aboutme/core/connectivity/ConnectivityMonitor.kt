package com.aboutme.core.connectivity

import com.aboutme.core.connectivity.model.ConnectivityStatus
import kotlinx.coroutines.flow.Flow

interface ConnectivityMonitor {

    fun connectivity(): Flow<ConnectivityStatus>

}