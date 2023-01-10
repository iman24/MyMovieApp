package com.imanancin.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.imanancin.core.domain.model.Movie
import com.imanancin.core.domain.usecase.MovieUseCase

class FavoriteViewModel(
    val moviePagingUseCase: MovieUseCase
): ViewModel() {


    fun getFavoriteMovies(): LiveData<PagingData<Movie>> {
        return moviePagingUseCase.getFavoriteMovies().asLiveData()
    }
}