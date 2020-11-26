package com.c0d3in3.movieapp.data.remote

import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.models.network.MovieDetailed
import com.c0d3in3.movieapp.models.network.MovieCollection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int) : Call<Movie>

    @GET("top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1) : MovieCollection

    @GET("popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1) : MovieCollection
}