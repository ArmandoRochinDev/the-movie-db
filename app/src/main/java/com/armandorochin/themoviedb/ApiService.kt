package com.armandorochin.themoviedb

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("discover/movie?api_key=b4fd0a29aa2683068f69dff6ae9bd659&language=es-MX&sort_by=popularity.desc")
    suspend fun getMovies():Response<MovieResponse>
}