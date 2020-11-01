package com.c0d3in3.movieapp

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c0d3in3.movieapp.data.local.AppDatabase

class App : Application() {

    private lateinit var roomDatabase: RoomDatabase

    override fun onCreate() {
        super.onCreate()

        roomDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }
}