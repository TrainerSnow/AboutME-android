package com.aboutme.core.connectivity.model

sealed class ConnectivityStatus(

    val hasInternet: Boolean

) {

    data object NoConnection: ConnectivityStatus(false)

    data class Connected(
        val wifi: Boolean
    ): ConnectivityStatus(true)

}
