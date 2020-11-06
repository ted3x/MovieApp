package com.c0d3in3.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c0d3in3.movieapp.data.local.AppDatabase.Companion.DATABASE_VERSION
import com.c0d3in3.movieapp.models.entity.Movie

@Database(entities = [Movie::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        private const val DATABASE_NAME = "MovieApp-DB"
        const val DATABASE_VERSION = 1

        fun build(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
    }
    abstract fun movieDao(): MovieDao

}