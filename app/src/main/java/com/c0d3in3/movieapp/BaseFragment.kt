package com.c0d3in3.movieapp

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.c0d3in3.movieapp.data.remote.NetworkConnectionListener
import com.c0d3in3.movieapp.ui.MovieActivity


abstract class BaseFragment : Fragment(), NetworkConnectionListener {

    private lateinit var connectivityReceiver: ConnectivityReceiver
    protected var isInternetAvailable =  MutableLiveData<Boolean>()
    private lateinit var navController: NavController

    init {
        isInternetAvailable.postValue(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        App.addNetworkConnectionListener(this)

        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

        connectivityReceiver = ConnectivityReceiver {
            isInternetAvailable.postValue(it)
        }
        activity?.registerReceiver(connectivityReceiver, filter)

        isInternetAvailable.observe(this, Observer {
            if(it) setErrorMessage(it, getString(R.string.back_in_online))
            else setErrorMessage(it, getString(R.string.you_are_offline))
        })
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(connectivityReceiver)
        App.removeNetworkConnectionListener()
        super.onDestroy()
    }

    override fun onInternetAvailable(message: String) {
        isInternetAvailable.postValue(true)
        setErrorMessage(true, getString(R.string.back_in_online))
    }

    override fun onInternetUnavailable(message: String) {
        isInternetAvailable.postValue(false)
        setErrorMessage(false, getString(R.string.you_are_offline))
    }

    private fun setErrorMessage(isInternetAvailable: Boolean, errorMessage : String){
        (activity as MovieActivity).setErrorMessage(isInternetAvailable, errorMessage)
    }

    protected fun openDetailedMovie(movieId: Int?) {
        navController.navigate(R.id.action_moviesDashboardFragment_to_movieDetailFragment,bundleOf("movieId" to movieId))
    }
}