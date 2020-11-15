package com.c0d3in3.movieapp.ui

import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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


class MovieActivity : AppCompatActivity(){

    private lateinit var  moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        setSupportActionBar(toolbarLayout.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)

        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

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

    fun setErrorMessage(isInternetAvailable: Boolean, errorMessage: String){
        if(isInternetAvailable && moviesViewModel.isInternetAvailable.value!!) return
        else moviesViewModel.errorMessage.postValue(errorMessage)
        moviesViewModel.isInternetAvailable.postValue(isInternetAvailable)
    }
}
