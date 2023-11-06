package com.armandorochin.themoviedb.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import retrofit2.HttpException
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1
private const val PAGE_LIMIT = 40764

@OptIn(ExperimentalPagingApi::class)
class DiscoveryMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
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
        try {
            val apiResponse = remoteDataSource.getDiscoveryMovies(pageIndex)
            val movies: List<MovieDto> = apiResponse.movies

            val endOfPaginationReached = (apiResponse.page == PAGE_LIMIT)

            if(LoadType.valueOf(loadType.name) == LoadType.REFRESH && apiResponse.page == 1){
                localDataSource.insertAndDeleteTransaction(movies.map{it.toMovieLocal(apiResponse.page)})
            }else{
                localDataSource.insertAll(movies.map { it.toMovieLocal(apiResponse.page) })
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRefreshKey():Int{
        val currentPage: Int = localDataSource.count() / 20
        return if(currentPage == 0) TMDB_STARTING_PAGE_INDEX else currentPage + 1
    }

    private suspend fun getNextKey():Int{
        val lastMovie = localDataSource.getLastCreatedMovie()


        return lastMovie.page + 1
    }
    private suspend fun getPrevKey():Int{
        val lastMovie = localDataSource.getFirstCreatedMovie()

        return lastMovie.page - 1
    }
}