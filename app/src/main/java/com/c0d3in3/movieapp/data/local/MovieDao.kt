package com.c0d3in3.movieapp.data.local

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.*
import com.c0d3in3.movieapp.models.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movies")
    fun getAllPaged(): DataSource.Factory<Int,Movie>

    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<Movie>

    @Query("SELECT * FROM movies WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Movie

    @Query("SELECT * FROM movies WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movies: Movie)

    @Delete
    fun delete(movie: Movie)
}