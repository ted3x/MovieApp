package com.c0d3in3.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.c0d3in3.movieapp.models.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}