package com.imanancin.mymoviedb.data

import androidx.paging.PagingData
import com.imanancin.mymoviedb.core.domain.model.Movie
import com.imanancin.mymoviedb.core.domain.model.MovieType
import com.imanancin.mymoviedb.core.domain.repository.IMovieRepository
import com.imanancin.mymoviedb.core.utils.DataMapper
import com.imanancin.mymoviedb.data.source.local.LocalDataSource
import com.imanancin.mymoviedb.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
): IMovieRepository {

    override fun getMovies(movieType: MovieType): Flow<PagingData<Movie>> {
        return remoteDataSource.getMovies(movieType).map {
            DataMapper.mapPagingToDomain(it)
        }
    }

    override suspend fun saveFavoriteMovie(favoriteMovie: Movie) {
        localDataSource.insertMovieFav(DataMapper.mapDomainToEntity(favoriteMovie))
    }

    override suspend fun deleteFavoriteMovie(favoriteMovie: Movie) {
        localDataSource.deleteFavoriteMovie(DataMapper.mapDomainToEntity(favoriteMovie))
    }

    override fun getFavoriteMovies(): Flow<PagingData<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            DataMapper.mapPagingEntityToDomain(it)
        }
    }

    override fun isFavoriteMovie(movie: Movie): Flow<Boolean> {
        return localDataSource.isFavoriteMovie(DataMapper.mapDomainToEntity(movie))
    }


}