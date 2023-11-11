package com.armandorochin.themoviedb.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.armandorochin.themoviedb.data.MoviesRepository
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_DISCOVER
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_NOW_PLAYING
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_TOP_RATED
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class RequestMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(category:String): LiveData<PagingData<Movie>> {
        return repository.getMovies(category)
    }
}