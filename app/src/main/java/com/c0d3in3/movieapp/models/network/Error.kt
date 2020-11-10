package com.c0d3in3.movieapp.models.network

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("status_message")
    val statusMessage: String,
    val success : Boolean,
    @SerializedName("status_code")
    val statusCode: Int
)