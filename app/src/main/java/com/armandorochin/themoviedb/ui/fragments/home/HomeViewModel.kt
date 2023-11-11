package com.armandorochin.themoviedb.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.armandorochin.themoviedb.domain.RequestMovies
import com.armandorochin.themoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    requestMovies: RequestMovies
) : ViewModel() {
    private val movies = requestMovies().cachedIn(viewModelScope)
    fun getMoviesLiveData(): LiveData<PagingData<Movie>> {
        return movies
    }
}