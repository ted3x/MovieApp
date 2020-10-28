package com.c0d3in3.movieapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Handler

class MoviesViewModel : ViewModel() {

    val isDataLoaded = MutableLiveData<Boolean>()

    init {
        isDataLoaded.value = false
    }
}