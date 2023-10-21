package com.armandorochin.themoviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.domain.RequestMovies
import com.armandorochin.themoviedb.domain.UpdateMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val requestMovies: RequestMovies,
    private val updateMovie: UpdateMovie
): ViewModel()  {

    private val movieList = requestMovies()

    fun moviesLivedata():LiveData<List<Movie>> {
       return movieList
    }

    fun changeFavStatus(movie: Movie){
        viewModelScope.launch {
            updateMovie(movie.copy(favorite = !movie.favorite))
        }
    }
}