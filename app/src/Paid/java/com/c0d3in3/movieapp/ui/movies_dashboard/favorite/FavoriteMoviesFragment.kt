package com.c0d3in3.movieapp.ui.movies_dashboard.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.c0d3in3.movieapp.BaseFragment
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import kotlinx.android.synthetic.Paid.favorites_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteMoviesFragment : BaseFragment() {

    private lateinit var adapter : MoviesAdapter
    private lateinit var viewModel : FavoriteMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[FavoriteMoviesViewModel::class.java]
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    @ExperimentalCoroutinesApi
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

        clearAllButton.setOnClickListener {
            viewModel.clearTable()
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
