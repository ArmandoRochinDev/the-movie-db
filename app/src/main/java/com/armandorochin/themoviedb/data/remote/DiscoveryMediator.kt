package com.armandorochin.themoviedb.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException
import java.util.Date

private const val TMDB_STARTING_PAGE_INDEX = 1
private const val PAGE_LIMIT = 40764

@OptIn(ExperimentalPagingApi::class)
class DiscoveryMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, Movie>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
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
            val movies: List<Movie> = apiResponse.movies.map {
                Movie(
                    movieId = it.movieId,
                    page = apiResponse.page, //save index page of the item
                    posterPath = it.posterPath,
                    adult = it.adult,
                    backdropPath = it.backdropPath,
                    genreIds = it.genreIds,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    title = it.title,
                    overview = it.overview,
                    popularity = it.popularity,
                    releaseDate = it.releaseDate,
                    video = it.video,
                    voteAverage = it.voteAverage,
                    voteCount =it.voteCount,
                    createdAt = Date()//current date
                )
            }

            val endOfPaginationReached = (apiResponse.page == PAGE_LIMIT)

            localDataSource.getDatabase().withTransaction {
                localDataSource.insertAll(movies)
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