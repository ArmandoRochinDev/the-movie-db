package com.armandorochin.themoviedb.ui.fragments.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.armandorochin.themoviedb.domain.RequestMovie
import com.armandorochin.themoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val requestMovie: RequestMovie
): ViewModel(){
    private lateinit var movie: LiveData<Movie>

    fun initViewModel(movieId:Int){
        movie = requestMovie(movieId)
    }
    fun getMovieLiveData(): LiveData<Movie> {
        return movie
    }
}