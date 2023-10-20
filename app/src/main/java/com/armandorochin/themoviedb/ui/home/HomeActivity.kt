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
                Log.d("HomeActivity", it.favorite.toString())
                binding.rvMovies.layoutManager = GridLayoutManager(this,2)
                binding.rvMovies.adapter = MoviesAdapter(movies) { movie ->
                    onMovieClicked(movie)
                }
            }
        })


    }

    fun onMovieClicked(movie: Movie){
        homeViewModel.changeFavStatus(movie)
    }
}