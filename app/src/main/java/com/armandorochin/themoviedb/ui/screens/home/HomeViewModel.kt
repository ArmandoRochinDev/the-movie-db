package com.armandorochin.themoviedb.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.armandorochin.themoviedb.domain.RequestDiscoveryMovies
import com.armandorochin.themoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    requestDiscoveryMovies: RequestDiscoveryMovies
) : ViewModel() {
    private val discoveryMovies = requestDiscoveryMovies().cachedIn(viewModelScope)
    fun getDiscoveryLiveData(): LiveData<PagingData<Movie>> {
        return discoveryMovies
    }
}