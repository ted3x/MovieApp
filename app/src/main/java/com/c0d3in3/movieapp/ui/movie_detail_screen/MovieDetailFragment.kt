package com.c0d3in3.movieapp.ui.movie_detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        viewModel.movie.observe(viewLifecycleOwner, Observer{
            (activity as MovieActivity).setToolbarTitle(it.title, true)
            backDropPoster.setImage(it.posterPath)
            releaseDate.text = "Release date: ${it.releaseDate}"
            rating.text = it.voteAverage.toString()
            originalTitle.text = "Original title: ${it.originalTitle}"
            overview.text = it.overview
        })
        super.onViewCreated(view, savedInstanceState)
    }
}
