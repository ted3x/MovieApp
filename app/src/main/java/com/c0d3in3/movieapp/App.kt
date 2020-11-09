package com.c0d3in3.movieapp

import android.app.Application
import android.content.Context
import com.c0d3in3.movieapp.data.local.AppDatabase

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