package com.armandorochin.themoviedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.armandorochin.themoviedb.data.MoviesRepository
import com.armandorochin.themoviedb.data.local.LocalDataSource
import com.armandorochin.themoviedb.data.local.movie.MovieLocal
import com.armandorochin.themoviedb.data.remote.RemoteDataSource
import com.armandorochin.themoviedb.domain.RequestMovie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class RequestMovieTest {
    private lateinit var useCase: RequestMovie
    private lateinit var repo: MoviesRepository
    @RelaxedMockK
    private lateinit var localDataSource: LocalDataSource
    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup(){
        MockKAnnotations.init(this)

        repo = MoviesRepository(localDataSource, remoteDataSource)
        useCase = RequestMovie(repo)
    }

    @Test
    fun `get movie from local db when request movie use case is called`() = runBlocking {
        //Given
        val movie = MovieLocal(
            movieId = 238, createdAt = Date(), page = 1, voteCount = 18974, video = false,
            title = "1972-03-14", voteAverage = 8.708, releaseDate = "1972-03-14", posterPath = "/ApiEfzSkrqS4m1L5a2GwWXzIiAs.jpg", popularity = 147.852,
            overview = "Don Vito Corleone es el respetado y temido jefe de una de las cinco familias de la mafia de Nueva York. Tiene cuatro hijos: una chica, Connie, y tres varones: el impulsivo Sonny, el pusilánime Freddie y Michael, que no quiere saber nada de los negocios de su padre. Cuando Corleone, siempre aconsejado por su consejero Tom Hagen, se niega a intervenir en el negocio de las drogas, el jefe de otra banda ordena su asesinato. Empieza entonces una violenta y cruenta guerra entre las familias mafiosas.",
            originalTitle = "The Godfather", originalLanguage = "en", backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
            genreIds = emptyList(), adult = false, uid = 238, category = "DISCOVER"
        )
        val _liveData = MutableLiveData<MovieLocal>()
        _liveData.value = movie
        val liveData: LiveData<MovieLocal> = _liveData
        coEvery { localDataSource.getMovie(movie.movieId) } returns liveData

        //when
        useCase.invoke(movie.movieId)//repo.getMovie(movie.movieId)

        //Then
        coVerify {
            localDataSource.getMovie(movie.movieId)
        }
    }
}