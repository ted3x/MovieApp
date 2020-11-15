package com.c0d3in3.movieapp.ui.movies_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.MovieActivity
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.TabAdapter
import com.c0d3in3.movieapp.ui.movies_dashboard.favorite.FavoriteMoviesFragment
import com.c0d3in3.movieapp.ui.movies_dashboard.popular.PopularMoviesFragment
import com.c0d3in3.movieapp.ui.movies_dashboard.top_rated.TopRatedMoviesFragment
import kotlinx.android.synthetic.main.fragment_movies_dashboard.*


class MoviesDashboardFragment : Fragment()  {

    private lateinit var adapter: TabAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = TabAdapter(childFragmentManager, 0)
        if(getString(R.string.current_flavor) == getString(R.string.free_flavor)) adapter.addFragment(PopularMoviesFragment(), "Popular")
        else adapter.addFragment(TopRatedMoviesFragment(), "Top Rated")
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
}
