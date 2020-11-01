package com.c0d3in3.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c0d3in3.movieapp.R

class MovieActivity : AppCompatActivity() {

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
    }
}
