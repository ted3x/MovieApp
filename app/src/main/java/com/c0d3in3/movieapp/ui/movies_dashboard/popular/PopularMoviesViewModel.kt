package com.c0d3in3.movieapp.ui.movies_dashboard.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.c0d3in3.movieapp.App
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.MovieTypes
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesSourcePaging
import kotlinx.coroutines.flow.Flow

class PopularMoviesViewModel: ViewModel() {
    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
        MoviesSourcePaging(MovieTypes.POPULAR, App.apiService)
    }.flow
        .cachedIn(viewModelScope)
}