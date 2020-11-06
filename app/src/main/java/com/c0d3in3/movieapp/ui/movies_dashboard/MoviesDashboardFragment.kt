package com.c0d3in3.movieapp.ui.movies_dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.MoviesViewModel
import com.c0d3in3.movieapp.ui.movies_dashboard.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies_dashboard.*


class MoviesDashboardFragment : Fragment(), AdapterView.OnItemSelectedListener, MoviesListener  {

    private lateinit var adapter: MoviesAdapter
    private val viewModel by activityViewModels<MoviesViewModel>()
    private lateinit var navController:NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.movies_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            moviesSpinner.adapter = adapter
        }
        moviesSpinner.onItemSelectedListener = this
        viewModel.moviesList.observe(viewLifecycleOwner, Observer{
            println("size ${it.size}")
            adapter = MoviesAdapter()
            adapter.setMovieList(it, this)
            moviesRecyclerView.adapter = adapter
        })

        moviesRecyclerView.layoutManager = GridLayoutManager(context, 3)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            when(parent.getItemAtPosition(position).toString()){
                getString(R.string.top_rated) -> viewModel.setMoviesType(MovieTypes.TOP_RATED)
                getString(R.string.popular) -> viewModel.setMoviesType(MovieTypes.POPULAR)
                getString(R.string.favorites) -> viewModel.setMoviesType(MovieTypes.FAVORITES)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
        super.onCreate(savedInstanceState)
    }

    override fun openDetailedMovie(position: Int) {
        viewModel.movie.value = viewModel.moviesList.value?.get(position)
        navController.navigate(R.id.action_moviesDashboardFragment_to_movieDetailFragment)
    }
}
