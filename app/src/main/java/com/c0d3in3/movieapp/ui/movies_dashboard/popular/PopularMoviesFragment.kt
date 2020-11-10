package com.c0d3in3.movieapp.ui.movies_dashboard.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import kotlinx.android.synthetic.main.no_network_layout.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PopularMoviesFragment : Fragment(), MoviesListener {

    private lateinit var adapter : MoviesAdapter
    private lateinit var navController: NavController
    private lateinit var viewModel : PopularMoviesViewModel

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

        adapter = MoviesAdapter(this)
        popularRV.layoutManager = GridLayoutManager(context, 3)
        popularRV.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                if(!parent.isInternetAvailable()!!) handleNetworkAvailabilityUI(false)
                else handleNetworkAvailabilityUI(true)
                if (popularSwipeLayout.isRefreshing) popularSwipeLayout.isRefreshing = false
                adapter.submitData(it)
            }
        }

        popularSwipeLayout.setOnRefreshListener {
            if (popularSwipeLayout.isRefreshing)
                adapter.refresh()
        }
        retryButton.setOnClickListener { if(parent.isInternetAvailable()!!) adapter.refresh() }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun openDetailedMovie(movie: Movie?) {
	   val parent = parentFragment as MoviesDashboardFragment
        parent.setSelectedMovie(movie)
        navController.navigate(R.id.action_moviesDashboardFragment_to_movieDetailFragment)
    }
}
