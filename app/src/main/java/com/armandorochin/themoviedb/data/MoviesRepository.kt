package com.armandorochin.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.map
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.local.movie.toMovie
import com.armandorochin.themoviedb.data.remote.MoviesMediator
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    private var _pager : LiveData<PagingData<Movie>>? = null
    private val pager get() = _pager!!
    @OptIn(ExperimentalPagingApi::class)
    private fun newInstance() {
        if(_pager == null){
            _pager = Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false, initialLoadSize = NETWORK_PAGE_SIZE),
                remoteMediator = MoviesMediator(localDataSource, remoteDataSource),
                pagingSourceFactory = { localDataSource.getMovies() }
            ).liveData.map {
                    pagingData ->
                pagingData.map { it.toMovie() }
            }
        }
    }

    fun getMovies(): LiveData<PagingData<Movie>>{
        newInstance()
        return pager
    }

    fun getMovie(movieId:Int): LiveData<Movie>{
        return localDataSource.getMovie(movieId).map { it.toMovie() }
    }
    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val TMDB_STARTING_PAGE_INDEX = 1
        const val PAGE_LIMIT = 40764
    }
}