package com.armandorochin.themoviedb.data.remote

import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesService: MoviesService
){
    suspend fun getMovies(): Response<MoviesBodyDto>{
        return moviesService.getMovies()
    }
}