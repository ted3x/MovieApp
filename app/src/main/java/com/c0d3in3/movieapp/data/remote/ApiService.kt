package com.c0d3in3.movieapp.data.remote

import com.c0d3in3.movieapp.models.network.Movie
import com.c0d3in3.movieapp.models.network.MovieCollection
import com.c0d3in3.movieapp.models.network.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int) : Call<Movie>

    @GET("top_rated")
    fun getTopRatedMovies(@Query("page") page: Int = 1) : Call<MovieCollection>

    @GET("popular")
    fun getPopularMovies(@Query("page") page: Int = 1) : Call<MovieCollection>
}