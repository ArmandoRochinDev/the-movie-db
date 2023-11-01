package com.armandorochin.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import com.armandorochin.themoviedb.data.remote.DiscoveryMediator
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun getDiscoveryMovies(): LiveData<PagingData<MovieLocal>>{
        val pagingSourceFactory = { localDataSource.getDiscoveryMovies() }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false, initialLoadSize = NETWORK_PAGE_SIZE),
            remoteMediator = DiscoveryMediator(localDataSource, remoteDataSource),
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}