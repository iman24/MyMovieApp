package com.imanancin.core.domain.usecase

import androidx.paging.PagingData
import com.imanancin.core.domain.model.Movie
import com.imanancin.core.domain.model.MovieType
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovies(movieType: MovieType): Flow<PagingData<Movie>>
    fun getFavoriteMovies(): Flow<PagingData<Movie>>
    suspend fun saveFavoriteMovie(movie: Movie)
    suspend fun deleteFavoriteMovie(movie: Movie)
    fun isFavoriteMovie(movie: Movie) : Flow<Boolean>
}