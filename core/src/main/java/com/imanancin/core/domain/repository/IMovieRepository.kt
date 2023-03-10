package com.imanancin.core.domain.repository

import androidx.paging.PagingData
import com.imanancin.core.domain.model.Movie
import com.imanancin.core.domain.model.MovieType
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(movieType: MovieType): Flow<PagingData<Movie>>
    suspend fun saveFavoriteMovie(favoriteMovie: Movie)
    suspend fun deleteFavoriteMovie(favoriteMovie: Movie)
    fun getFavoriteMovies(): Flow<PagingData<Movie>>
    fun isFavoriteMovie(movie: Movie): Flow<Boolean>
}