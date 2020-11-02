package com.c0d3in3.movieapp.models.network

import com.google.gson.annotations.SerializedName


data class MovieDetailed (

    val adult : Boolean,
    @SerializedName("backdrop_path") val backdropPath : String?,
    @SerializedName("belongs_to_collection") val belongsToCollection : Collection?,
    val budget : Int,
    val genres : List<Genres>,
    val homepage : String?,
    val id : Int,
    @SerializedName("imdb_id") val imdbId : String?,
    @SerializedName("original_language") val originalLanguage : String,
    @SerializedName("original_title") val originalTitle : String,
    val overview : String?,
    val popularity : Double,
    @SerializedName("poster_path") val posterPath : String?,
    @SerializedName("production_companies") val productionCompanies : List<ProductionCompanies>,
    @SerializedName("production_countries") val productionCountries : List<ProductionCountries>,
    @SerializedName("release_date") val releaseDate : String,
    val revenue : Int,
    val runtime : Int?,
    @SerializedName("spoken_languages") val spokenLanguages : List<SpokenLanguages>,
    val status : String,
    val tagline : String?,
    val title : String,
    val video : Boolean,
    @SerializedName("vote_average") val voteAverage : Double,
    @SerializedName("vote_count") val voteCount : Int
)