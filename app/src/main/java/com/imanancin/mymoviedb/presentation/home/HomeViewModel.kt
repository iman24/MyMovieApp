package com.imanancin.mymoviedb.presentation.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.imanancin.mymoviedb.core.domain.model.Movie
import com.imanancin.mymoviedb.core.domain.model.MovieType
import com.imanancin.mymoviedb.core.domain.usecase.MovieUseCase

class HomeViewModel(
    val moviePagingUseCase: MovieUseCase
): ViewModel() {

    private val movieTypez : MutableLiveData<MovieType> = MutableLiveData<MovieType>(MovieType.TRENDING)

    fun movies(): LiveData<PagingData<Movie>>  {
        return movieTypez.switchMap { data ->
            moviePagingUseCase.getMovies(data).asLiveData()
        }
    }

    fun setMovieType (movieType: MovieType) {
        movieTypez.value = movieType
    }

    fun getFavoriteMovies(): LiveData<PagingData<Movie>> {
        return moviePagingUseCase.getFavoriteMovies().asLiveData()
    }
}