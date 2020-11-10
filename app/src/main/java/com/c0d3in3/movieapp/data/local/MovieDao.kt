package com.c0d3in3.movieapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.c0d3in3.movieapp.models.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<Movie>

    @Query("SELECT * FROM movies WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Movie

    @Insert
    fun insert(vararg movies: Movie)

    @Delete
    fun delete(movie: Movie)
}