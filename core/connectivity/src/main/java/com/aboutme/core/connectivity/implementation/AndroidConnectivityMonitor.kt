package com.aboutme.core.connectivity.implementation;

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.aboutme.core.connectivity.ConnectivityMonitor
import com.aboutme.core.connectivity.model.ConnectivityStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@SuppressLint("NewApi")
internal class AndroidConnectivityMonitor(
    private val conMan: ConnectivityManager
) : ConnectivityMonitor {

    override fun connectivity(): Flow<ConnectivityStatus> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {

            override fun onLost(network: Network) {
                super.onLost(network)
                channel.trySend(ConnectivityStatus.NoConnection)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                channel.trySend(
                    ConnectivityStatus.Connected(networkCapabilities.isWifi())
                )
                super.onCapabilitiesChanged(network, networkCapabilities)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        conMan.registerNetworkCallback(request, callback)

        val caps = conMan.getNetworkCapabilities(conMan.activeNetwork)
        channel.trySend(
            if (caps == null) ConnectivityStatus.NoConnection
            else ConnectivityStatus.Connected(caps.isWifi())
        )

        awaitClose { conMan.unregisterNetworkCallback(callback) }
    }

    private fun NetworkCapabilities.isWifi() =
        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
}