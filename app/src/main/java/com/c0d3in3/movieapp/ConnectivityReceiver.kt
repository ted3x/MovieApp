package com.c0d3in3.movieapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.c0d3in3.movieapp.data.remote.NetworkConnectionListener
import java.net.HttpURLConnection
import java.net.URL

class ConnectivityReceiver(private var isInternetAvailable: (Boolean) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            if (actNw == null) isInternetAvailable(false)
            else {
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> isInternetAvailable(true)
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> isInternetAvailable(true)
                    else -> isInternetAvailable(false)
                }
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo
            if (nwInfo.isConnected) isInternetAvailable(true)
            else isInternetAvailable(false)
        }
    }
}