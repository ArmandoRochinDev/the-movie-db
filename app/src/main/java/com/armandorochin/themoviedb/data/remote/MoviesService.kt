package com.armandorochin.themoviedb.data.remote

import com.armandorochin.themoviedb.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface MoviesService {
    @GET("discover/movie?language=es-MX")
    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_TOKEN}")
    suspend fun getMovies():Response<MoviesBodyDto>
}