package com.c0d3in3.movieapp.ui

import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.c0d3in3.movieapp.App
import com.c0d3in3.movieapp.ConnectivityReceiver
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.data.remote.NetworkConnectionListener
import com.c0d3in3.movieapp.utils.Constants.ERROR_MESSAGE_DELAY
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.view.*
import kotlinx.android.synthetic.main.custom_toolbar_layout.view.*


class MovieActivity : AppCompatActivity(), NetworkConnectionListener{

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

        val comReceiver = ConnectivityReceiver(this)

        baseContext.registerReceiver(comReceiver, intentFilter)
        setSupportActionBar(toolbarLayout.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)

        moviesViewModel.errorMessage.observe(this, Observer{
            messageLayout.visibility = View.VISIBLE
            messageLayout.message.text = it
            if(it == getString(R.string.back_in_online)) Handler().postDelayed({messageLayout.visibility = View.GONE}, ERROR_MESSAGE_DELAY)
        })
    }

    fun setToolbarTitle(title: String, enableBackButton: Boolean){
        val toolbarTV = findViewById<TextView>(R.id.toolbarTV)
        if(toolbarLayout.visibility == View.GONE) toolbarLayout.visibility = View.VISIBLE
        toolbarTV.text = title
        supportActionBar?.setDisplayHomeAsUpEnabled(enableBackButton)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    override fun onInternetAvailable() {
        if(moviesViewModel.isInternetAvailable.value != null)
            if(!moviesViewModel.isInternetAvailable.value!!) moviesViewModel.errorMessage.postValue(getString(R.string.back_in_online))
        moviesViewModel.isInternetAvailable.postValue(true)
    }

    override fun onInternetUnavailable() {
        moviesViewModel.errorMessage.postValue(getString(R.string.you_are_offline))
        moviesViewModel.isInternetAvailable.postValue(false)
    }

    override fun onPause() {
        (application as App).removeNetworkConnectionListener()
        super.onPause()
    }

    override fun onResume() {
        (application as App).addNetworkConnectionListener(this)
        super.onResume()
    }
}
