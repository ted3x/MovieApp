package com.c0d3in3.movieapp.ui.movies_dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.MoviesViewModel
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies_dashboard.*


class MoviesDashboardFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    private lateinit var adapter: MoviesAdapter
    private lateinit var moviesViewModel : MoviesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            moviesViewModel = ViewModelProvider(it)[MoviesViewModel::class.java]
        }
        return inflater.inflate(R.layout.fragment_movies_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.movies_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            moviesSpinner.adapter = adapter
        }
        moviesSpinner.onItemSelectedListener = this
        moviesViewModel.moviesList.observe(viewLifecycleOwner, Observer{
            println("size ${it.size}")
            adapter = MoviesAdapter()
            adapter.setMovieList(it)
            moviesRecyclerView.layoutManager = GridLayoutManager(context, 3)
            moviesRecyclerView.adapter = adapter
        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            when(parent.getItemAtPosition(position).toString()){
                getString(R.string.top_rated) -> moviesViewModel.setMoviesType(MovieTypes.TOP_RATED)
                getString(R.string.popular) -> moviesViewModel.setMoviesType(MovieTypes.POPULAR)
                getString(R.string.favorites) -> moviesViewModel.setMoviesType(MovieTypes.FAVORITES)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
        super.onCreate(savedInstanceState)
    }
}
