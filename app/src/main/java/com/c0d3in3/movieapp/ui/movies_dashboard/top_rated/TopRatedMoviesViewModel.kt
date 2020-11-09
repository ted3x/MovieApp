package com.c0d3in3.movieapp.ui.movies_dashboard.top_rated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.top_rated.paging.TopRatedMoviesDataSource
import com.c0d3in3.movieapp.ui.movies_dashboard.top_rated.paging.TopRatedMoviesDataSourceFactory

class TopRatedMoviesViewModel: ViewModel() {
    var moviesList: LiveData<PagedList<Movie>>? = null
    private var topRatedMoviesDataSource = MutableLiveData<TopRatedMoviesDataSource>()

    init{
        val moviesDataSourceFactory =
            TopRatedMoviesDataSourceFactory()
        topRatedMoviesDataSource = moviesDataSourceFactory.moviesDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(TopRatedMoviesDataSource.PAGE_SIZE).build()
        moviesList =
            LivePagedListBuilder<Int, Movie>(moviesDataSourceFactory, pagedListConfig)
                .build()
    }

    fun refreshData(){
        topRatedMoviesDataSource.value?.invalidate()
    }
}