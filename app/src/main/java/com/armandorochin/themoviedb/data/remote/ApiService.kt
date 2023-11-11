package com.armandorochin.themoviedb.data.remote

import com.armandorochin.themoviedb.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?language=es-MX")
    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_TOKEN}")
    suspend fun getMoviesDiscover(@Query("page") page:Int):ServiceResponse
    @GET("movie/now_playing?language=es-MX")
    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_TOKEN}")
    suspend fun getMoviesNowPlaying(@Query("page") page:Int):ServiceResponse
    @GET("movie/top_rated?language=es-MX")
    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_TOKEN}")
    suspend fun getMoviesTopRated(@Query("page") page:Int):ServiceResponse
    @GET("movie/upcoming?language=es-MX")
    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_TOKEN}")
    suspend fun getMoviesUpcoming(@Query("page") page:Int):ServiceResponse
}