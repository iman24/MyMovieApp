package com.imanancin.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imanancin.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}