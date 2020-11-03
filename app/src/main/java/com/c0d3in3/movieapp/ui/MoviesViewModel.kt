package com.c0d3in3.movieapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c0d3in3.movieapp.data.local.repository.MovieRepositoryImpl
import com.c0d3in3.movieapp.data.remote.RetrofitClient
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.models.network.MovieCollection
import com.c0d3in3.movieapp.models.network.MovieDetailed
import com.c0d3in3.movieapp.ui.movies_dashboard.MovieTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    val isDataLoaded = MutableLiveData<Boolean>()
    val moviesList = MutableLiveData<List<Movie>>()
    private val selectedMoviesType = MutableLiveData<MovieTypes>()
    private val currentPage = MutableLiveData<Int>()
    private val repository = MovieRepositoryImpl()

    val movie = MutableLiveData<Movie>()
    val errorMessage = MutableLiveData<String>()

    init {
        isDataLoaded.value = false
        selectedMoviesType.value = MovieTypes.TOP_RATED
        currentPage.value = 1
    }

    fun setMoviesType(type: MovieTypes){
        selectedMoviesType.value = type
        currentPage.value = 1
        fetchData()
    }

    fun fetchData(){
        when(selectedMoviesType.value){
            MovieTypes.FAVORITES -> {
                viewModelScope.launch(Dispatchers.IO){
                    val list = repository.getFavouriteMovies()
                    withContext(Dispatchers.Main) {
                        moviesList.value = list
                    }
                }
            }
            MovieTypes.POPULAR -> {
                RetrofitClient.service.getPopularMovies(currentPage.value!!).enqueue(object: Callback<MovieCollection>{
                    override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                        errorMessage.value = t.toString()
                    }

                    override fun onResponse(
                        call: Call<MovieCollection>,
                        response: Response<MovieCollection>
                    ) {
                        moviesList.value = response.body()?.results
                        isDataLoaded.value = true
                    }
                })
            }
            MovieTypes.TOP_RATED -> {
                RetrofitClient.service.getTopRatedMovies(currentPage.value!!).enqueue(object: Callback<MovieCollection>{
                    override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<MovieCollection>,
                        response: Response<MovieCollection>
                    ) {
                        moviesList.value = response.body()?.results
                        isDataLoaded.value = true
                    }
                })
            }
        }
    }
}