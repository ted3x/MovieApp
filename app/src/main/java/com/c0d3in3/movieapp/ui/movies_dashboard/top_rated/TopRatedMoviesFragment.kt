package com.c0d3in3.movieapp.ui.movies_dashboard.top_rated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesDashboardFragment
import com.c0d3in3.movieapp.ui.movies_dashboard.MoviesListener
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_top_rated_movies.*

class TopRatedMoviesFragment : Fragment(), MoviesListener{

    private lateinit var adapter : MoviesAdapter
    private lateinit var navController: NavController
    private lateinit var viewModel : TopRatedMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)

        adapter = MoviesAdapter(this)
        topRatedRV.layoutManager = GridLayoutManager(context, 3)
        topRatedRV.adapter = adapter

        viewModel.moviesList?.observe(viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun openDetailedMovie(position: Int) {
        val parent = parentFragment as MoviesDashboardFragment
        parent.setSelectedMovie(viewModel.moviesList?.value?.get(position))
        navController.navigate(R.id.action_moviesDashboardFragment_to_movieDetailFragment)
    }
}
