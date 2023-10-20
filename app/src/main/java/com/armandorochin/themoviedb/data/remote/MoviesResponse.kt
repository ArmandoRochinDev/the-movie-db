package com.armandorochin.themoviedb.data.remote

data class MoviesResponse(
    val page: Int,
    val movieRemotes: List<MovieRemote>,
    val total_pages: Int,
    val total_results: Int
)