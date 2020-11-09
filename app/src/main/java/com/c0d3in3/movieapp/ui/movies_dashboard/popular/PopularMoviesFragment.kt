package com.c0d3in3.movieapp.ui.movies_dashboard.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesDashboardFragment
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesListener
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import kotlinx.android.synthetic.main.no_network_layout.*


class PopularMoviesFragment : Fragment(), MoviesListener {

    private lateinit var adapter: MoviesAdapter
    private lateinit var navController: NavController
    private lateinit var viewModel: PopularMoviesViewModel
    private lateinit var parent: MoviesDashboardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        parent = parentFragment as MoviesDashboardFragment
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            viewModel = ViewModelProvider(it)[PopularMoviesViewModel::class.java]
        }
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)

        handleNetworkAvailabilityUI(parent.isInternetAvailable()!!)
        adapter = MoviesAdapter(this)
        popularRV.layoutManager = GridLayoutManager(context, 3)
        popularRV.adapter = adapter

        viewModel.moviesList?.observe(viewLifecycleOwner, Observer {
            if(!parent.isInternetAvailable()!! && it.isEmpty()) handleNetworkAvailabilityUI(false)
            else handleNetworkAvailabilityUI(true)
            adapter.submitList(it)
            if (popularSwipeLayout.isRefreshing) popularSwipeLayout.isRefreshing = false
        })

        popularSwipeLayout.setOnRefreshListener {
            if (popularSwipeLayout.isRefreshing)
                viewModel.refreshData()
        }

        retryButton.setOnClickListener { viewModel.refreshData() }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun openDetailedMovie(position: Int) {
        parent.setSelectedMovie(viewModel.moviesList?.value?.get(position))
        navController.navigate(R.id.action_moviesDashboardFragment_to_movieDetailFragment)
    }

    private fun handleNetworkAvailabilityUI(networkAvailable: Boolean) {
        if (networkAvailable) {
            popularRV.visibility = View.VISIBLE
            noNetworkLayout.visibility = View.GONE
        } else {
            popularRV.visibility = View.GONE
            noNetworkLayout.visibility = View.VISIBLE
        }
    }
}
