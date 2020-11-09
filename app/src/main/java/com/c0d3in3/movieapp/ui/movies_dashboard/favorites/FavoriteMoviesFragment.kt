package com.c0d3in3.movieapp.ui.movies_dashboard.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesDashboardFragment
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesListener
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import com.c0d3in3.movieapp.ui.movies_dashboard.popular.PopularMoviesViewModel
import kotlinx.android.synthetic.main.fragment_favorite_movies.*
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteMoviesFragment : Fragment(), MoviesListener {

    private lateinit var adapter : MoviesAdapter
    private lateinit var navController: NavController
    private lateinit var viewModel : FavoriteMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            viewModel = ViewModelProvider(it)[FavoriteMoviesViewModel::class.java]
        }
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)

        adapter = MoviesAdapter(this)
        favoritesRV.layoutManager = GridLayoutManager(context, 3)
        favoritesRV.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun openDetailedMovie(movie: Movie?) {
        val parent = parentFragment as MoviesDashboardFragment
        parent.setSelectedMovie(movie)
        navController.navigate(R.id.action_moviesDashboardFragment_to_movieDetailFragment)
    }
}
