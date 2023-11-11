package com.armandorochin.themoviedb.ui.fragments.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.armandorochin.themoviedb.data.remote.RemoteMediator
import com.armandorochin.themoviedb.domain.RequestMovies
import com.armandorochin.themoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    requestMovies: RequestMovies
): ViewModel()  {
    private val moviesDiscover = requestMovies(RemoteMediator.CAT_DISCOVER).cachedIn(viewModelScope)
    private val moviesNowPlaying = requestMovies(RemoteMediator.CAT_NOW_PLAYING).cachedIn(viewModelScope)
    private val moviesTopRated = requestMovies(RemoteMediator.CAT_TOP_RATED).cachedIn(viewModelScope)
    private val moviesUpcoming = requestMovies(RemoteMediator.CAT_UPCOMING).cachedIn(viewModelScope)
    fun getMoviesLiveData(category: String): LiveData<PagingData<Movie>> {
        return when(category){
            RemoteMediator.CAT_DISCOVER -> {
                moviesDiscover
            }
            RemoteMediator.CAT_NOW_PLAYING -> {
                moviesNowPlaying
            }
            RemoteMediator.CAT_TOP_RATED -> {
                moviesTopRated
            }
            RemoteMediator.CAT_UPCOMING -> {
                moviesUpcoming
            }
            else -> {
                moviesDiscover
            }
        }
    }
}
