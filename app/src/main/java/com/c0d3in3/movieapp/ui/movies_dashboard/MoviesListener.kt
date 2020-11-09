package com.c0d3in3.movieapp.ui.movies_dashboard

import com.c0d3in3.movieapp.models.entity.Movie

interface MoviesListener {
    fun openDetailedMovie(movie : Movie?)
}