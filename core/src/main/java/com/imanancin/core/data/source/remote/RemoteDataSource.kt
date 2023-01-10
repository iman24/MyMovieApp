package com.imanancin.core.data.source.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.imanancin.core.domain.model.MovieType
import com.imanancin.core.data.source.remote.network.ApiResponse
import com.imanancin.core.data.source.remote.network.ApiService
import com.imanancin.core.data.source.remote.paging.MoviesPagingSource
import com.imanancin.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    val apiService: ApiService
) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

    }


    fun getMovies(movieType: MovieType): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(apiService, movieType)
            }
        ).flow.flowOn(Dispatchers.IO)
    }


}