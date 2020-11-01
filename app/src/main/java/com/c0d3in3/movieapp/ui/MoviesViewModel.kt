package com.c0d3in3.movieapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c0d3in3.movieapp.data.remote.RetrofitClient
import com.c0d3in3.movieapp.models.network.MovieCollection
import com.c0d3in3.movieapp.models.network.Results
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    val isDataLoaded = MutableLiveData<Boolean>()
    val popularMovies = MutableLiveData<List<Results>>()

    init {
        isDataLoaded.value = false
    }
}