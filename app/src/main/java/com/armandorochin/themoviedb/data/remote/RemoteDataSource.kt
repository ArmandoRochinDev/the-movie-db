package com.armandorochin.themoviedb.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getMovies(page: Int): ServiceResponse{
        return apiService.getMovies(page)
    }
}