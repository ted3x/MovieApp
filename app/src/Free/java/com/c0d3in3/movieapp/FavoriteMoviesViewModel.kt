package com.c0d3in3.movieapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.c0d3in3.movieapp.data.local.repository.MovieRepositoryImpl
import com.c0d3in3.movieapp.models.entity.Movie
import kotlinx.coroutines.flow.Flow

class FavoriteMoviesViewModel : ViewModel() {
    private val repositoryImpl = MovieRepositoryImpl()
    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20),
        pagingSourceFactory = {repositoryImpl.getFavouriteMovies()}  ).flow
        .cachedIn(viewModelScope)
}