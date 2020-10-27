package com.c0d3in3.movieapp.ui.splash_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import com.c0d3in3.movieapp.R
import com.c0d3in3.movieapp.ui.SharedViewModel


class SplashScreenFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            sharedViewModel = ViewModelProvider(it)[SharedViewModel::class.java]
        }
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = Navigation.findNavController(view)
        sharedViewModel.isDataLoaded.observe(viewLifecycleOwner, Observer{
            activity?.setTheme(R.style.AppTheme)
            if(it) navController.navigate(R.id.action_splashScreenFragment_to_moviesDashboardFragment)
        })
        super.onViewCreated(view, savedInstanceState)
    }
}
