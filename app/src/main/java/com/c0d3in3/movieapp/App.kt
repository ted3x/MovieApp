package com.c0d3in3.movieapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.c0d3in3.movieapp.data.local.AppDatabase
import com.c0d3in3.movieapp.utils.Constants

class App : Application() {


    companion object{
        val roomDatabase: AppDatabase by lazy{
            AppDatabase.build(context)
        }
        private lateinit var context : Context
    }

    override fun onCreate() {
        context = this
        super.onCreate()
    }
}