package com.c0d3in3.movieapp.ui.movies_dashboard.top_rated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.c0d3in3.movieapp.BaseFragment
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_top_rated_movies.*
import kotlinx.android.synthetic.main.no_network_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopRatedMoviesFragment : BaseFragment(){

    private lateinit var adapter : MoviesAdapter
    private lateinit var viewModel : TopRatedMoviesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[TopRatedMoviesViewModel::class.java]
        return inflater.inflate(R.layout.fragment_top_rated_movies, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MoviesAdapter {
            openDetailedMovie(it)
        }
        topRatedRV.layoutManager = GridLayoutManager(context, 3)
        topRatedRV.adapter = adapter

        isInternetAvailable.observe(viewLifecycleOwner, Observer {
            handleNetworkAvailabilityUI(it)
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                if (topRatedSwipeLayout.isRefreshing) topRatedSwipeLayout.isRefreshing = false
                adapter.submitData(it)
            }
        }
       topRatedSwipeLayout.setOnRefreshListener {
            if (topRatedSwipeLayout.isRefreshing)
                adapter.refresh()
        }
        retryButton.setOnClickListener { adapter.refresh() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun handleNetworkAvailabilityUI(isInternetAvailable: Boolean) {
        if (isInternetAvailable) {
            topRatedRV.visibility = View.VISIBLE
            noNetworkLayout.visibility = View.GONE
        } else {
            topRatedRV.visibility = View.GONE
            noNetworkLayout.visibility = View.VISIBLE
        }
    }
}
