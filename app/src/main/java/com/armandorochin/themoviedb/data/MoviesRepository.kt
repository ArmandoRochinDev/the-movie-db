package com.armandorochin.themoviedb.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.map
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import com.armandorochin.themoviedb.data.local.movie.toMovie
import com.armandorochin.themoviedb.data.remote.RemoteMediator
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_DISCOVER
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_NOW_PLAYING
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_TOP_RATED
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_UPCOMING
import com.armandorochin.themoviedb.domain.model.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    private var _pagerDiscover : LiveData<PagingData<Movie>>? = null
    private var _pagerNowPlaying : LiveData<PagingData<Movie>>? = null
    private var _pagerTopRated : LiveData<PagingData<Movie>>? = null
    private var _pagerUpcoming : LiveData<PagingData<Movie>>? = null
    private val pagerDiscover get() = _pagerDiscover!!
    private val pagerNowPlaying get() = _pagerNowPlaying!!
    private val pagerTopRated get() = _pagerTopRated!!
    private val pagerUpcoming get() = _pagerUpcoming!!

    private fun newInstance(category: String):LiveData<PagingData<Movie>> {
        return when(category){
            CAT_DISCOVER -> {
                _pagerDiscover = newInstancePager(_pagerDiscover, CAT_DISCOVER)
                pagerDiscover
            }
            CAT_NOW_PLAYING -> {
                _pagerNowPlaying = newInstancePager(_pagerNowPlaying, CAT_NOW_PLAYING)
                pagerNowPlaying
            }
            CAT_TOP_RATED -> {
                _pagerTopRated = newInstancePager(_pagerTopRated, CAT_TOP_RATED)
                pagerTopRated
            }
            CAT_UPCOMING -> {
                _pagerUpcoming = newInstancePager(_pagerUpcoming, CAT_UPCOMING)
                pagerUpcoming
            }
            else -> {
                _pagerDiscover = newInstancePager(_pagerDiscover, CAT_DISCOVER)
                pagerDiscover
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun newInstancePager(_pager: LiveData<PagingData<Movie>>?, category: String): LiveData<PagingData<Movie>> {
        var pager = _pager
        return if(pager == null){
            pager = Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false, initialLoadSize = NETWORK_PAGE_SIZE),
                remoteMediator = RemoteMediator(localDataSource, remoteDataSource, category),
                pagingSourceFactory = { localDataSource.getMovies(category) }
            ).liveData.map { pagingData ->  pagingData.map { it.toMovie() }}
            pager
        }else{
            pager
        }
    }

    fun getMovies(category:String): LiveData<PagingData<Movie>>{
        return newInstance(category)
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