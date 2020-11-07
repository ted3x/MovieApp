package com.c0d3in3.movieapp.ui.movies_dashboard.top_rated.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.top_rated.paging.TopRatedMoviesDataSource


class TopRatedMoviesDataSourceFactory:
    DataSource.Factory<Int, Movie?>() {
    val moviesDataSource =
        MutableLiveData<TopRatedMoviesDataSource>()

    override fun create(): DataSource<Int, Movie?> {
        val itemDataSource =
            TopRatedMoviesDataSource()
        moviesDataSource.postValue(itemDataSource)
        return itemDataSource
    }

}
