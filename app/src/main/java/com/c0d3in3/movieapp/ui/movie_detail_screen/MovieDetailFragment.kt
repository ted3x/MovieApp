package com.c0d3in3.movieapp.ui.movie_detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.extensions.setImage
import com.c0d3in3.movieapp.ui.MovieActivity
import com.c0d3in3.movieapp.ui.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {


    private val viewModel by activityViewModels<MoviesViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)

        viewModel.isFavoriteMovie.observe(viewLifecycleOwner, Observer{
            if(it){
                favoriteButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_favorited, 0)
                favoriteButton.text = "Remove from favorites"
            }
            else {
                favoriteButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_favorite, 0)
                favoriteButton.text = "Add to favorites"
            }
        })

        viewModel.movie.observe(viewLifecycleOwner, Observer{
            (activity as MovieActivity).setToolbarTitle(it.title, true)
            backDropPoster.setImage(it.posterUrl)
            releaseDate.text = "Release date: ${it.releaseDate}"
            rating.text = it.voteAverage.toString()
            originalTitle.text = "Original title: ${it.originalTitle}"
            overview.text = it.overview
        })

        viewModel.checkMovieForFavorite()

        favoriteButton.setOnClickListener{
            viewModel.handleFavoriteControl()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
