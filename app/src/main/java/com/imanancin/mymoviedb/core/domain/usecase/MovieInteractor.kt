package com.imanancin.mymoviedb.core.domain.usecase

import androidx.paging.PagingData
import com.imanancin.mymoviedb.core.domain.model.Movie
import com.imanancin.mymoviedb.core.domain.model.MovieType
import com.imanancin.mymoviedb.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(
    val movieRepository: IMovieRepository
): MovieUseCase {
    override fun getMovies(movieType: MovieType): Flow<PagingData<Movie>> {
        return movieRepository.getMovies(movieType)
    }

    override fun getFavoriteMovies(): Flow<PagingData<Movie>> {
        return movieRepository.getFavoriteMovies()
    }

    override suspend fun saveFavoriteMovie(movie: Movie) {
        return movieRepository.saveFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        movieRepository.deleteFavoriteMovie(movie)
    }

    override fun isFavoriteMovie(movie: Movie): Flow<Boolean> {
        return movieRepository.isFavoriteMovie(movie)
    }




}