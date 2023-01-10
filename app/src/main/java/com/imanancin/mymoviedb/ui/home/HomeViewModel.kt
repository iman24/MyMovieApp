package com.imanancin.mymoviedb.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.imanancin.core.domain.model.Movie
import com.imanancin.core.domain.model.MovieType
import com.imanancin.core.domain.usecase.MovieUseCase

class HomeViewModel(
    val moviePagingUseCase: MovieUseCase
): ViewModel() {

    private val movieTypez : MutableLiveData<MovieType> = MutableLiveData<MovieType>()

    fun movies(): LiveData<PagingData<Movie>>  {
        return movieTypez.switchMap { data ->
            moviePagingUseCase.getMovies(data).cachedIn(viewModelScope).asLiveData()
        }
    }

    fun setMovieType (movieType: MovieType) {
        movieTypez.value = movieType
    }

}