package com.c0d3in3.movieapp.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.utils.Constants.ERROR_MESSAGE_DELAY
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.view.*

class MovieActivity : AppCompatActivity() {

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)

        moviesViewModel.fetchData()

        moviesViewModel.errorMessage.observe(this, Observer{
            messageLayout.visibility = View.VISIBLE
            messageLayout.message.text = it
            Handler().postDelayed({messageLayout.visibility = View.GONE}, ERROR_MESSAGE_DELAY)
        })
    }

    fun setToolbarTitle(title: String, enableBackButton: Boolean){
        if(toolbar.visibility == View.GONE) toolbar.visibility = View.VISIBLE
        toolbar.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(enableBackButton)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
