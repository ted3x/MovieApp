package com.c0d3in3.movieapp.ui.movies_dashboard.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.c0d3in3.movieapp.BaseFragment
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import kotlinx.android.synthetic.Free.favorites_movie_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavoriteMoviesFragment: BaseFragment() {

    private lateinit var adapter : MoviesAdapter
    private lateinit var viewModel : FavoriteMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[FavoriteMoviesViewModel::class.java]
        return inflater.inflate(R.layout.favorites_movie_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MoviesAdapter {
            openDetailedMovie(it)
        }
        favoritesRV.layoutManager = GridLayoutManager(context, 3)
        favoritesRV.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
