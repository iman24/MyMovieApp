package com.imanancin.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imanancin.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_movies WHERE id = :id)")
    fun isFavoriteMovie(id : String) : Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favorite: MovieEntity)

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun deleteFavoriteMovie(id: String)

}