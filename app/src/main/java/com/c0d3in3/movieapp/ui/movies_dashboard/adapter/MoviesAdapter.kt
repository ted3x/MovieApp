package com.c0d3in3.movieapp.ui.movies_dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.extensions.setImage
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesDashboardFragment
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesListener

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private var moviesList = listOf<Movie>()
    private lateinit var moviesListener: MoviesListener

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false))
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val imageView = holder.itemView as ImageView
        imageView.setImage(moviesList[position].posterUrl)
        imageView.setOnClickListener {
            moviesListener.openDetailedMovie(position)
        }
    }

    fun setMovieList(mList: List<Movie>, fragment: MoviesDashboardFragment){
        moviesList = mList
        moviesListener = fragment
    }
}