package com.c0d3in3.movieapp.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie (
    @PrimaryKey val id : Int,

    @SerializedName("original_title")
    @ColumnInfo(name="original_title") val originalTitle : String,

    @ColumnInfo(name="overview") val overview : String,

    @SerializedName("poster_path")
    @ColumnInfo(name="poster_path") val posterPath : String?,

    @SerializedName("release_date")
    @ColumnInfo(name="release_date") val releaseDate : String,

    @ColumnInfo(name="title") val title : String,

    @SerializedName("vote_average")
    @ColumnInfo(name="vote_average") val voteAverage : Double
)