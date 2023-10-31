package com.armandorochin.themoviedb.di

import com.armandorochin.themoviedb.data.remote.DiscoveryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASEURL = "https://api.themoviedb.org/3/"
    const val IMAGEURL = "https://image.tmdb.org/t/p/w185/"
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
    fun provideMoviesService(retrofit: Retrofit): DiscoveryService{
        return retrofit.create(DiscoveryService::class.java)
    }
}