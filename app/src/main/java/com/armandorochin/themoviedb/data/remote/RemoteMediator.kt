package com.armandorochin.themoviedb.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.armandorochin.themoviedb.data.MoviesRepository.Companion.PAGE_LIMIT
import com.armandorochin.themoviedb.data.MoviesRepository.Companion.TMDB_STARTING_PAGE_INDEX
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import retrofit2.HttpException
import java.io.IOException
@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val category: String
) : RemoteMediator<Int, MovieLocal>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieLocal>
    ): MediatorResult {
        val pageIndex = when (loadType) {
            LoadType.REFRESH -> {
                val key = getRefreshKey()
                key
            }
            LoadType.PREPEND -> {
                val prevKey = getPrevKey()

                if (prevKey < 1){
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val nextKey = getNextKey()

                if(nextKey == PAGE_LIMIT){
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                nextKey
            }
        }
        return try {
            val apiResponse = fetchDataFromServer(pageIndex)

            val endOfPaginationReached = (apiResponse.page == PAGE_LIMIT)

            saveDataIntoLocalDb(loadType, apiResponse)

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun saveDataIntoLocalDb(loadType: LoadType, apiResponse: ServiceResponse) {
        if(LoadType.valueOf(loadType.name) == LoadType.REFRESH && apiResponse.page == 1){
            localDataSource.insertAndDeleteTransaction(apiResponse.movies.map{it.toMovieLocal(apiResponse.page, category)}, category)
        }else{
            localDataSource.insertAll(apiResponse.movies.map { it.toMovieLocal(apiResponse.page, category) })
        }
    }

    private suspend fun fetchDataFromServer(pageIndex: Int) : ServiceResponse {
        return when(category){
            CAT_DISCOVER -> {
                remoteDataSource.getMoviesDiscover(pageIndex)
            }
            CAT_NOW_PLAYING -> {
                remoteDataSource.getMoviesNowPlaying(pageIndex)
            }
            CAT_TOP_RATED -> {
                remoteDataSource.getMoviesTopRated(pageIndex)
            }
            CAT_UPCOMING -> {
                remoteDataSource.getMoviesUpcoming(pageIndex)
            }

            else -> {remoteDataSource.getMoviesDiscover(pageIndex)}
        }
    }

    private suspend fun getRefreshKey():Int{
        val currentPage: Int = localDataSource.count(category) / 20
        return if(currentPage == 0) TMDB_STARTING_PAGE_INDEX else currentPage + 1
    }

    private suspend fun getNextKey():Int{
        val lastMovie = localDataSource.getLastCreatedMovie(category)


        return lastMovie.page + 1
    }
    private suspend fun getPrevKey():Int{
        val lastMovie = localDataSource.getFirstCreatedMovie(category)

        return lastMovie.page - 1
    }

    companion object{
        const val CAT_DISCOVER = "DISCOVER"
        const val CAT_NOW_PLAYING = "NOW_PLAYING"
        const val CAT_TOP_RATED = "TOP_RATED"
        const val CAT_UPCOMING = "UPCOMING"
    }
}