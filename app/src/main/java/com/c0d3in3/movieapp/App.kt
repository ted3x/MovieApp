package com.c0d3in3.movieapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.c0d3in3.movieapp.data.local.AppDatabase
import com.c0d3in3.movieapp.utils.Constants

class App : Application() {


    companion object{
        var roomDatabase: AppDatabase? = null
        var context : Context? = null
    }

    override fun onCreate() {

        roomDatabase = Room.databaseBuilder(this,
            AppDatabase::class.java, Constants.DATABASE_NAME
        ).build()
        context = this
        super.onCreate()
    }
}