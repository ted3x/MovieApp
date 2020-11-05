package com.c0d3in3.movieapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.utils.Constants.IMAGES_BASE_URL

fun ImageView.setImage(url: String?){
    if(url!= null) {
        Glide.with(this).load("$IMAGES_BASE_URL$url").placeholder(R.drawable.ic_logo).into(this)
    }
}