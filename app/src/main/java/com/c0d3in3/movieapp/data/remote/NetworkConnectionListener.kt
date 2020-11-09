package com.c0d3in3.movieapp.data.remote

interface NetworkConnectionListener {
    fun onInternetAvailable()
    fun onInternetUnavailable()
}