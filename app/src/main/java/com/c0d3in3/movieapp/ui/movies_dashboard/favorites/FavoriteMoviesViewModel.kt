package com.c0d3in3.movieapp.ui.movies_dashboard.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.c0d3in3.movieapp.App
import com.c0d3in3.movieapp.models.entity.Movie

class FavoriteMoviesViewModel: ViewModel() {

    companion object{
        const val PAGE_SIZE = 20
    }
    var moviesList: LiveData<PagedList<Movie>>

    init {
        val factory: DataSource.Factory<Int, Movie> =
           App.roomDatabase.movieDao().getAllPaged()

        val pagedListBuilder: LivePagedListBuilder<Int, Movie> = LivePagedListBuilder(factory,
            PAGE_SIZE)
        moviesList = pagedListBuilder.build()
    }
}