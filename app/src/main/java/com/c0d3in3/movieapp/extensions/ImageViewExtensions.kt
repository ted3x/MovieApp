package com.c0d3in3.movieapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.c0d3in3.movieapp.R

fun ImageView.setImage(url: String?){
    if(url!= null) {
        Glide.with(this).load("https://image.tmdb.org/t/p/original$url").placeholder(R.drawable.ic_logo).into(this)
    }
}