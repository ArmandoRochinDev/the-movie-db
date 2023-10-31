package com.armandorochin.themoviedb.data.remote

import com.armandorochin.themoviedb.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DiscoveryService {
    @GET("discover/movie?language=es-MX&sort_by=popularity.desc")
    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_TOKEN}")
    suspend fun getDiscoveryMovies(@Query("page") page:Int):DiscoveryResponse
}