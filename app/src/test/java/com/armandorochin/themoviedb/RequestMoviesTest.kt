package com.armandorochin.themoviedb

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import com.armandorochin.themoviedb.data.MoviesRepository
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import com.armandorochin.themoviedb.data.remote.RemoteMediator
import com.armandorochin.themoviedb.data.remote.ServiceResponse
import com.armandorochin.themoviedb.domain.RequestMovies
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
class RequestMoviesTest {
    private lateinit var mediator: RemoteMediator
    @RelaxedMockK
    private lateinit var localDataSource: LocalDataSource
    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup(){
        MockKAnnotations.init(this)

        mediator = RemoteMediator(localDataSource, remoteDataSource, RemoteMediator.CAT_DISCOVER)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `when db is empty get movies from remote`() = runBlocking{
        //Given
        val pagingState = mockk<PagingState<Int,  MovieLocal>>()
        val movie = MovieLocal(
            movieId = 238, createdAt = Date(), page = 1, voteCount = 18974, video = false,
            title = "1972-03-14", voteAverage = 8.708, releaseDate = "1972-03-14", posterPath = "/ApiEfzSkrqS4m1L5a2GwWXzIiAs.jpg", popularity = 147.852,
            overview = "Don Vito Corleone es el respetado y temido jefe de una de las cinco familias de la mafia de Nueva York. Tiene cuatro hijos: una chica, Connie, y tres varones: el impulsivo Sonny, el pusilánime Freddie y Michael, que no quiere saber nada de los negocios de su padre. Cuando Corleone, siempre aconsejado por su consejero Tom Hagen, se niega a intervenir en el negocio de las drogas, el jefe de otra banda ordena su asesinato. Empieza entonces una violenta y cruenta guerra entre las familias mafiosas.",
            originalTitle = "The Godfather", originalLanguage = "en", backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
            genreIds = emptyList(), adult = false, uid = 238, category = RemoteMediator.CAT_DISCOVER
        )

        coEvery { localDataSource.count(RemoteMediator.CAT_DISCOVER) } returns 0
        coEvery { localDataSource.getLastCreatedMovie(RemoteMediator.CAT_DISCOVER) } returns movie
        coEvery { localDataSource.getFirstCreatedMovie(RemoteMediator.CAT_DISCOVER) } returns movie
        coEvery { remoteDataSource.getMoviesDiscover(1) } returns ServiceResponse(movies = emptyList(), page = 1, totalResults = 0, totalPages = 0)

        //When
        mediator.load(LoadType.REFRESH, pagingState)

        //Then
        coVerify {
            remoteDataSource.getMoviesDiscover(1)
        }
    }
}