package com.imanancin.core.data.source.remote.network

import com.imanancin.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query(value = "page") page: Int): ListMovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query(value = "page") page: Int): ListMovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query(value = "page") page: Int): ListMovieResponse

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query(value = "page") page: Int): ListMovieResponse

}