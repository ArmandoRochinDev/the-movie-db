package com.armandorochin.themoviedb.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.armandorochin.themoviedb.databinding.ActivityHomeBinding
import com.armandorochin.themoviedb.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler(emptyList())

        homeViewModel.moviesLivedata().observe(this){ movies ->
            (binding.rvMovies.adapter as MoviesAdapter).updateList(movies)
        }

    }

    private fun setupRecycler(movies: List<Movie>) {
        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)
        binding.rvMovies.adapter = MoviesAdapter(movies){
            onMovieClicked(it)
        }
    }

    private fun onMovieClicked(movie: Movie){
        homeViewModel.changeFavStatus(movie)
    }
}