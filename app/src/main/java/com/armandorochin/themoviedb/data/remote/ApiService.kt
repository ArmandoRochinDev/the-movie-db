package com.armandorochin.themoviedb.data.remote

import com.armandorochin.themoviedb.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?language=es-MX")
    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_TOKEN}")
    suspend fun getMovies(@Query("page") page:Int):ServiceResponse
}