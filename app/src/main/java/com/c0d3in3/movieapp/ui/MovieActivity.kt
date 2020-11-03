package com.c0d3in3.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c0d3in3.movieapp.R
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.view.*

class MovieActivity : AppCompatActivity() {

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        moviesViewModel.fetchData()

        moviesViewModel.errorMessage.observe(this, Observer{
            messageLayout.visibility = View.VISIBLE
            messageLayout.message.text = it
            messageLayout.visibility = View.VISIBLE
            Handler().postDelayed({messageLayout.visibility = View.GONE}, 2500)
        })
    }
}
