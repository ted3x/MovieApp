package com.c0d3in3.movieapp.ui.movies_dashboard.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.popular.paging.PopularMoviesDataSource
import com.c0d3in3.movieapp.ui.movies_dashboard.popular.paging.PopularMoviesDataSourceFactory

class PopularMoviesViewModel: ViewModel() {

    var moviesList: LiveData<PagedList<Movie>>? = null
    private var popularMoviesDataSource = MutableLiveData<PopularMoviesDataSource>()

    init{
        val moviesDataSourceFactory =
            PopularMoviesDataSourceFactory()
        popularMoviesDataSource = moviesDataSourceFactory.moviesDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(PopularMoviesDataSource.PAGE_SIZE).build()
        moviesList =
            LivePagedListBuilder<Int, Movie>(moviesDataSourceFactory, pagedListConfig)
                .build()
    }

    fun refreshData(){
        popularMoviesDataSource.value?.invalidate()
    }
}