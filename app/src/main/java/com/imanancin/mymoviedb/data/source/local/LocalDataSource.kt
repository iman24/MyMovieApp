package com.imanancin.mymoviedb.data.source.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.imanancin.mymoviedb.data.source.local.entity.MovieEntity
import com.imanancin.mymoviedb.data.source.local.room.FavoriteMovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(val movieDao: FavoriteMovieDao) {

    fun getFavoriteMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                movieDao.getFavoriteMovies()
            }
        ).flow.flowOn(Dispatchers.IO)
    }

    suspend fun insertMovieFav(movie: MovieEntity) = movieDao.insertFavoriteMovie(movie)
    suspend fun deleteFavoriteMovie(movie: MovieEntity) = movieDao.deleteFavoriteMovie(movie.id)
    fun isFavoriteMovie(movie: MovieEntity) = movieDao.isFavoriteMovie(movie.id)


}