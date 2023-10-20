package com.armandorochin.themoviedb.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.armandorochin.themoviedb.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel.getAllMovies()

        homeViewModel.moviesList?.observe(this, Observer { movies ->
            //TODO setup ui with movie list
            movies.forEach {
                Log.d("HomeActivity", it.title)
            }
        })
    }
}