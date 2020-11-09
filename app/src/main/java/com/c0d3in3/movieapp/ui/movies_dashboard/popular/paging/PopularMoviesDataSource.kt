package com.c0d3in3.movieapp.ui.movies_dashboard.popular.paging

import androidx.paging.PageKeyedDataSource
import com.c0d3in3.movieapp.data.remote.RetrofitClient
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.models.network.MovieCollection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PopularMoviesDataSource : PageKeyedDataSource<Int, Movie?>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie?>
    ) {
        RetrofitClient.service.getPopularMovies(FIRST_PAGE)
            .enqueue(object : Callback<MovieCollection> {
                override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<MovieCollection>,
                    response: Response<MovieCollection>
                ) {
                    println("movida popular")
                    response.body()?.results?.let {
                        callback.onResult(
                            it,
                            null,
                            FIRST_PAGE + 1
                        )
                    }
                }
            })
    }

    //this will load the previous page
    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie?>
    ) {
    }

    //this will load the next page
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie?>
    ) {
        RetrofitClient.service.getPopularMovies(params.key)
            .enqueue(object : Callback<MovieCollection> {
                override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<MovieCollection>,
                    response: Response<MovieCollection>
                ) {
                    println("movida popular1")
                    val key =
                        if (response.body()!!.totalPages > params.key) params.key + 1 else null

                    //passing the loaded data and next page value
                    callback.onResult(response.body()!!.results, key)
                }
            })
    }

    companion object {
        private const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
    }
}