package com.c0d3in3.movieapp.models.network

import com.c0d3in3.movieapp.models.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieCollection (

	val page : Int,
	val results : List<Movie>,
	@SerializedName("total_results") val totalResults : Int,
	@SerializedName("total_pages") val totalPages : Int
)