package com.armandorochin.themoviedb.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getMoviesDiscover(page: Int): ServiceResponse{
        return apiService.getMoviesDiscover(page)
    }
    suspend fun getMoviesNowPlaying(page: Int): ServiceResponse{
        return apiService.getMoviesNowPlaying(page)
    }
    suspend fun getMoviesTopRated(page: Int): ServiceResponse{
        return apiService.getMoviesTopRated(page)
    }
    suspend fun getMoviesUpcoming(page: Int): ServiceResponse{
        return apiService.getMoviesUpcoming(page)
    }
}