package com.c0d3in3.movieapp.ui.splash_screen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.c0d3in3.movieapp.utils.Constants.SPLASH_SCREEN_DELAY
import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.MoviesViewModel


class SplashScreenFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var handler: Handler
    private lateinit var navController: NavController
    private lateinit var runnable: Runnable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        handler = Handler()
        activity?.let {
            moviesViewModel = ViewModelProvider(it)[MoviesViewModel::class.java]
        }
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        runnable = Runnable{
            navController.navigate(R.id.action_splashScreenFragment_to_moviesDashboardFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        handler.removeCallbacks(runnable)
        super.onPause()
    }

    override fun onResume() {
        handler.postDelayed(runnable, SPLASH_SCREEN_DELAY)
        super.onResume()
    }

}
