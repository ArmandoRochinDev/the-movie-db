package com.armandorochin.themoviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.domain.GetMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovies: GetMovies
): ViewModel()  {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList:LiveData<List<Movie>> get() = _moviesList

    fun getAllMovies() {
        viewModelScope.launch {
            try {
                val movies = getMovies()
                _moviesList.value = movies
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}