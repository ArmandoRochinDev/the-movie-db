package com.armandorochin.themoviedb.di

import com.armandorochin.themoviedb.data.remote.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASEURL = "https://api.themoviedb.org/3/"
    const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNGZkMGEyOWFhMjY4MzA2OGY2OWRmZjZhZTliZDY1OSIsInN1YiI6IjY1MWM1MzI2OGMyMmMwMDEzZDEyMzllZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.UNXuhznLuFXech3ddULSQbH9E-g45zkXQ7ceCvlrp2Y"
    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService{
        return retrofit.create(MoviesService::class.java)
    }
}