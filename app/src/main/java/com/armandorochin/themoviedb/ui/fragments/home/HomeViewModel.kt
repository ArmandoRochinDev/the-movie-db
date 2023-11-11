package com.armandorochin.themoviedb.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_DISCOVER
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_NOW_PLAYING
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_TOP_RATED
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_UPCOMING
import com.armandorochin.themoviedb.domain.RequestMovies
import com.armandorochin.themoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    requestMovies: RequestMovies
) : ViewModel() {
    private val moviesDiscover = requestMovies(CAT_DISCOVER).cachedIn(viewModelScope)
    private val moviesNowPlaying = requestMovies(CAT_NOW_PLAYING).cachedIn(viewModelScope)
    private val moviesTopRated = requestMovies(CAT_TOP_RATED).cachedIn(viewModelScope)
    private val moviesUpcoming = requestMovies(CAT_UPCOMING).cachedIn(viewModelScope)
    fun getMoviesLiveData(category: String): LiveData<PagingData<Movie>> {
        return when(category){
            CAT_DISCOVER -> {
                moviesDiscover
            }
            CAT_NOW_PLAYING -> {
                moviesNowPlaying
            }
            CAT_TOP_RATED -> {
                moviesTopRated
            }
            CAT_UPCOMING -> {
                moviesUpcoming
            }
            else -> {
                moviesDiscover
            }
        }
    }
}