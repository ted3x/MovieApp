package com.c0d3in3.movieapp.ui.movies_dashboard.top_rated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesDashboardFragment
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.models.entity.Movie
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesListener
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_top_rated_movies.*
import kotlinx.android.synthetic.main.no_network_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopRatedMoviesFragment : Fragment(), MoviesListener{

    private lateinit var adapter : MoviesAdapter
    private lateinit var navController: NavController
    private lateinit var viewModel : TopRatedMoviesViewModel
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
            viewModel = ViewModelProvider(it)[TopRatedMoviesViewModel::class.java]
        }
        return inflater.inflate(R.layout.fragment_top_rated_movies, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)

        handleNetworkAvailabilityUI(parent.isInternetAvailable()!!)
        adapter = MoviesAdapter(this)
        topRatedRV.layoutManager = GridLayoutManager(context, 3)
        topRatedRV.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                if(!parent.isInternetAvailable()!!) handleNetworkAvailabilityUI(false)
                else handleNetworkAvailabilityUI(true)
                if (topRatedSwipeLayout.isRefreshing) topRatedSwipeLayout.isRefreshing = false
                adapter.submitData(it)
            }
        }
       topRatedSwipeLayout.setOnRefreshListener {
            if (topRatedSwipeLayout.isRefreshing)
                adapter.refresh()
        }
        retryButton.setOnClickListener { if(parent.isInternetAvailable()!!) adapter.refresh() }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun openDetailedMovie(movie: Movie?) {
        parent.setSelectedMovie(movie)
        navController.navigate(R.id.action_moviesDashboardFragment_to_movieDetailFragment)
    }

    private fun handleNetworkAvailabilityUI(networkAvailable: Boolean) {
        if (networkAvailable) {
            topRatedRV.visibility = View.VISIBLE
            noNetworkLayout.visibility = View.GONE
        } else {
            topRatedRV.visibility = View.GONE
            noNetworkLayout.visibility = View.VISIBLE
        }
    }
}
