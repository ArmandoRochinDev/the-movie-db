package com.armandorochin.themoviedb.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.local.TmdbDatabase
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import com.armandorochin.themoviedb.data.local.movie.MoviesDao
import com.armandorochin.themoviedb.data.remote.RemoteMediator.Companion.CAT_DISCOVER
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date


@RunWith(RobolectricTestRunner::class)
class RemoteMediatorTest{
    private lateinit var moviesDao: MoviesDao
    private lateinit var db: TmdbDatabase
    @RelaxedMockK
    private lateinit var localDataSource: LocalDataSource
    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun createDb(){
        MockKAnnotations.init(this)

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TmdbDatabase::class.java
        ).allowMainThreadQueries().build()

        moviesDao = db.moviesDao()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `when load type is refresh and page is one delete and insert movies`() = runBlocking {
        //Given
        coEvery { localDataSource.count(CAT_DISCOVER) } returns 0
        coEvery { remoteDataSource.getMoviesDiscover(1) } returns ServiceResponse(movies = emptyList(), page = 1, totalResults = 0, totalPages = 0)
        val pagingState = mockk<PagingState<Int,  MovieLocal>>()
        val mediator = RemoteMediator(localDataSource, remoteDataSource, CAT_DISCOVER)
        val response = remoteDataSource.getMoviesDiscover(1)


        //When
        mediator.load(LoadType.REFRESH, pagingState)

        //Then
        coVerify {
            localDataSource.insertAndDeleteTransaction(response.movies.map { it.toMovieLocal(response.page, CAT_DISCOVER) }, CAT_DISCOVER)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `when load type is append and page is not one insert movies`() = runBlocking {
        //Given
        coEvery { localDataSource.getLastCreatedMovie(CAT_DISCOVER) } returns MovieLocal(uid = 1, adult = false, backdropPath = "", genreIds = emptyList(), movieId = 1, originalLanguage = "", originalTitle = "", overview = "", popularity = 1.0, posterPath = "", releaseDate = "", title = "", video = false, voteAverage = 1.0, voteCount = 1, page = 2, createdAt = Date(), category = CAT_DISCOVER )
        coEvery { remoteDataSource.getMoviesDiscover(1) } returns ServiceResponse(movies = emptyList(), page = 2, totalResults = 0, totalPages = 0)
        val pagingState = mockk<PagingState<Int,  MovieLocal>>()
        val mediator = RemoteMediator(localDataSource, remoteDataSource, CAT_DISCOVER)
        val response = remoteDataSource.getMoviesDiscover(1)


        //When
        mediator.load(LoadType.APPEND, pagingState)

        //Then
        coVerify {
            localDataSource.insertAll(response.movies.map { it.toMovieLocal(response.page, CAT_DISCOVER) })
        }
    }
}