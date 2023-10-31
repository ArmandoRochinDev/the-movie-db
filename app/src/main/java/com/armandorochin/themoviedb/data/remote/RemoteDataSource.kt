package com.armandorochin.themoviedb.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val discoveryService: DiscoveryService
){
    suspend fun getDiscoveryMovies(page: Int): DiscoveryResponse{
        return discoveryService.getDiscoveryMovies(page)
    }
}