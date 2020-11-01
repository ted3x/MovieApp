package com.c0d3in3.movieapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.c0d3in3.movieapp.models.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieentity")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE id IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): MovieEntity

    @Insert
    fun insertAll(vararg movieEntities: MovieEntity)

    @Delete
    fun delete(movieEntity: MovieEntity)
}