package com.imanancin.mymoviedb.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.imanancin.mymoviedb.core.domain.model.Movie
import com.imanancin.mymoviedb.core.domain.usecase.MovieUseCase

class DetailViewModel(
    val movieUseCase: MovieUseCase
): ViewModel() {

    suspend fun addToFavorite(movie: Movie) {
        movieUseCase.saveFavoriteMovie(movie)
    }

    suspend fun deleteFavorite(movie: Movie) {
        movieUseCase.deleteFavoriteMovie(movie)
    }


    fun isFavorite(movie: Movie): LiveData<Boolean> {
        return movieUseCase.isFavoriteMovie(movie).asLiveData()
    }

}