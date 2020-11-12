package com.c0d3in3.movieapp.data.local.repository

import androidx.paging.PagedList
import androidx.paging.PagingSource
import com.c0d3in3.movieapp.App.Companion.roomDatabase
import com.c0d3in3.movieapp.models.entity.Movie

class MovieRepositoryImpl {

    private val movieDao = roomDatabase.movieDao()

    fun getMovieById(id: Int) = movieDao.findById(id)

    fun getFavouriteMovies() = movieDao.getAll()

    fun addFavouriteMovie(movie: Movie) = movieDao.insert(movie)

    fun deleteFavouriteMovie(movie: Movie) = movieDao.delete(movie)
}