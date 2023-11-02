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
import com.armandorochin.themoviedb.data.remote.DiscoveryMediator
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun getDiscoveryMovies(): LiveData<PagingData<Movie>>{
        val pagingSourceFactory = { localDataSource.getDiscoveryMovies() }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false, initialLoadSize = NETWORK_PAGE_SIZE),
            remoteMediator = DiscoveryMediator(localDataSource, remoteDataSource),
            pagingSourceFactory = pagingSourceFactory
        ).liveData.map {
                pagingData ->
            pagingData.map { it.toMovie() }
        }
    }
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}