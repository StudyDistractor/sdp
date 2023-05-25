package com.github.studydistractor.sdp.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

/**
 * A utils class that tell the current online status of the application based on the
 * connectivityManager
 */
class OnlineStatus {
    /**
     * Return the current online status based on the connectivity status
     * @param connectivityManager based on the current context
     * @return True if the app is online and false if not
     */
    fun isOnline(connectivityManager: ConnectivityManager): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }
}