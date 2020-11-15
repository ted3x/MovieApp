package com.c0d3in3.movieapp.ui.movies_dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.extensions.setImage
import com.c0d3in3.movieapp.models.entity.Movie

class MoviesAdapter(private val openDetailedMovie : (Int?) -> Unit) :
    PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(DiffUtilCallBack()) {


    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        val imageView = holder.itemView as ImageView
        item?.posterUrl?.let { imageView.setImage(it) }
        imageView.setOnClickListener {
            openDetailedMovie(item?.id)
        }
    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}