package com.c0d3in3.movieapp.ui.movies_dashboard.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.c0d3in3.movieapp.App
import com.c0d3in3.movieapp.data.local.repository.MovieRepositoryImpl
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.MovieTypes
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesSourcePaging
import kotlinx.coroutines.flow.Flow

class FavoriteMoviesViewModel: ViewModel() {

    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20),
    pagingSourceFactory = {MovieRepositoryImpl().getFavouriteMovies()}  ).flow
        .cachedIn(viewModelScope)
}