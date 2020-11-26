package com.c0d3in3.movieapp.ui.movie_detail_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c0d3in3.movieapp.App
import com.c0d3in3.movieapp.data.local.repository.MovieRepositoryImpl
import com.c0d3in3.movieapp.models.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : ViewModel() {
    val selectedMovie = MutableLiveData<Movie>()
    private val repository = MovieRepositoryImpl()
    var isFavoriteMovie = MutableLiveData<Boolean>()

    fun loadMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = repository.getMovieById(movieId)
            if (movie != null) {
                selectedMovie.postValue(movie)
                isFavoriteMovie.postValue(true)
            } else {
                App.apiService.getMovie(movieId).enqueue(object : Callback<Movie> {
                    override fun onFailure(call: Call<Movie>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                        selectedMovie.postValue(response.body())
                        isFavoriteMovie.postValue(false)
                    }
                })
            }
        }
    }

    private fun checkMovieForFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            isFavoriteMovie.postValue(repository.getMovieById(selectedMovie.value?.id!!) != null)
        }
    }

    fun handleFavoriteControl() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedMovie.value?.let {
                if (isFavoriteMovie.value!!) repository.deleteFavouriteMovie(it)
                else repository.addFavouriteMovie(it)
            }
            checkMovieForFavorite()
        }
    }
}