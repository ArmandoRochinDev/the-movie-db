package com.armandorochin.themoviedb.ui.screens.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.armandorochin.themoviedb.domain.RequestDiscoveryMovie
import com.armandorochin.themoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val requestDiscoveryMovie: RequestDiscoveryMovie
): ViewModel(){
    private lateinit var discoveryMovie: LiveData<Movie>

    fun initViewModel(movieId:Int){
        discoveryMovie = requestDiscoveryMovie(movieId)
    }
    fun getDiscoveryMovieLiveData(): LiveData<Movie> {
        return discoveryMovie
    }
}