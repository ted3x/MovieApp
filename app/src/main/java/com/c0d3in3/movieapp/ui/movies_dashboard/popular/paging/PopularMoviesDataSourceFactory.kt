package com.c0d3in3.movieapp.ui.movies_dashboard.popular.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.popular.paging.PopularMoviesDataSource


class PopularMoviesDataSourceFactory:
    DataSource.Factory<Int, Movie?>() {
    val moviesDataSource =
        MutableLiveData<PopularMoviesDataSource>()

    override fun create(): DataSource<Int, Movie?> {
        val itemDataSource =
            PopularMoviesDataSource()
        moviesDataSource.postValue(itemDataSource)
        return itemDataSource
    }

}
