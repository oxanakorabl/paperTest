package com.paperapp.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkHelper(context: Context) {

    private val connectivityManager =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)


    //TODO remove depricated method
    fun isOnline() = connectivityManager.activeNetworkInfo?.isConnected ?: false
    fun isOffline() = !isOnline()
}