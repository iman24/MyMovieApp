package com.imanancin.mymoviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.imanancin.core.domain.model.Movie
import com.imanancin.core.domain.usecase.MovieUseCase

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