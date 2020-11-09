package com.c0d3in3.movieapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.c0d3in3.movieapp.data.local.AppDatabase
import com.c0d3in3.movieapp.data.remote.ApiService
import com.c0d3in3.movieapp.data.remote.NetworkConnectionListener
import com.c0d3in3.movieapp.data.remote.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class App : Application() {

    private var networkConnectionListener: NetworkConnectionListener? = null

    companion object{
        val roomDatabase: AppDatabase by lazy{
            AppDatabase.build(context)
        }
        lateinit var context : Context
        lateinit var apiService :ApiService
    }

    override fun onCreate() {
        context = this

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        super.onCreate()
    }

    fun addNetworkConnectionListener(listener: NetworkConnectionListener){
        networkConnectionListener = listener
    }

    fun removeNetworkConnectionListener(){
        networkConnectionListener = null
    }


    private fun provideOkHttpClient() : OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)

        okHttpClientBuilder.addInterceptor { chain ->
            val original: Request = chain.request()
            val httpUrl = original.url()
            val newHttpUrl = httpUrl.newBuilder().addQueryParameter("api_key", "d2fb147fa09a0d7db11b5db59eeda053").build()
            val request = original.newBuilder().url(newHttpUrl).build()
            chain.proceed(request)
        }

        okHttpClientBuilder.addInterceptor(object : RequestInterceptor() {
            override val isInternetAvailable: Boolean
                get() = checkConnectivity()

            override fun onInternetUnavailable() {
                networkConnectionListener?.onInternetUnavailable()
            }

            override fun onInternetAvailable() {
                networkConnectionListener?.onInternetAvailable()
            }

        })
        return okHttpClientBuilder.build()
    }

    private fun isInternetAvailable(): Boolean {
        val url = URL("https://www.google.com");
        try {
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 3000
            urlConnection.connect()
            return urlConnection.responseCode == 200
        } catch (ex: Exception) {
        }
        return false
    }

    fun checkConnectivity(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> isInternetAvailable()
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> isInternetAvailable()
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return if(nwInfo.isConnected) isInternetAvailable()
            else false
        }
    }
}