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
    val errorMessage = MutableLiveData<String>()
    val isInternetAvailable = MutableLiveData<Boolean>()

    init {
        isInternetAvailable.value = true
    }
}