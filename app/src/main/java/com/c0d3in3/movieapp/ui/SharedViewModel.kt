package com.c0d3in3.movieapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Handler

class SharedViewModel : ViewModel() {

    val isDataLoaded: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isDataLoaded.value = false
    }
}