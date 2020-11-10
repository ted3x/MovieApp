package com.c0d3in3.movieapp.ui.movies_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.c0d3in3.movieapp.FavoriteMoviesFragment
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.MovieActivity
import com.c0d3in3.movieapp.ui.MoviesViewModel
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.TabAdapter
import com.c0d3in3.movieapp.ui.movies_dashboard.popular.PopularMoviesFragment
import com.c0d3in3.movieapp.ui.movies_dashboard.top_rated.TopRatedMoviesFragment
import kotlinx.android.synthetic.main.fragment_movies_dashboard.*


class MoviesDashboardFragment : Fragment()  {

    private lateinit var adapter: TabAdapter
    private val viewModel by activityViewModels<MoviesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TabAdapter(childFragmentManager, 0)
        adapter.addFragment(TopRatedMoviesFragment(), "Top Rated")
        adapter.addFragment(PopularMoviesFragment(), "Popular")
        adapter.addFragment(FavoriteMoviesFragment(), "Favorites")
        dashboardViewPager.adapter = adapter

        tabLayout.setupWithViewPager(dashboardViewPager)
        (activity as MovieActivity).setToolbarTitle("Dashboard", false)

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
        super.onCreate(savedInstanceState)
    }

    fun setSelectedMovie(movie: Movie?){
        if(movie != null) viewModel.movie.value = movie
    }

    fun isInternetAvailable() : Boolean? = viewModel.isInternetAvailable.value
}
