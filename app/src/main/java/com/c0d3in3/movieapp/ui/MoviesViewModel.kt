package com.c0d3in3.movieapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c0d3in3.movieapp.data.local.repository.MovieRepositoryImpl
import com.c0d3in3.movieapp.models.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesViewModel : ViewModel() {
    val movie = MutableLiveData<Movie>()
    val isFavoriteMovie = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private val repository = MovieRepositoryImpl()


    fun checkMovieForFavorite(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                isFavoriteMovie.postValue(repository.getMovieById(movie.value?.id!!) != null)
            }
        }
    }

    fun handleFavoriteControl(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movie.value?.let {
                    if(isFavoriteMovie.value!!) repository.deleteFavouriteMovie(it)
                    else repository.addFavouriteMovie(it)
                }
                checkMovieForFavorite()
            }
        }
    }
}