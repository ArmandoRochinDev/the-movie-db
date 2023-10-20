package com.armandorochin.themoviedb.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesService: MoviesService
){
    suspend fun getMovies(): MoviesResponse{
        return moviesService.getMovies()
    }
}