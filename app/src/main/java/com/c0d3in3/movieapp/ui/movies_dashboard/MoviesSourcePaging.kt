package com.c0d3in3.movieapp.ui.movies_dashboard

import androidx.paging.PagingSource
import com.c0d3in3.movieapp.data.remote.ApiService
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.models.network.MovieCollection

class MoviesSourcePaging(private val movieTypes: MovieTypes, private val apiService: ApiService) : PagingSource<Int,Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val response : MovieCollection = if(movieTypes == MovieTypes.TOP_RATED) apiService.getTopRatedMovies(nextPage)
            else apiService.getPopularMovies(nextPage)

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = response.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}