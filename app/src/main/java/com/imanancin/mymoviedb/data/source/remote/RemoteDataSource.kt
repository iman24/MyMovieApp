package com.imanancin.mymoviedb.data.source.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.imanancin.mymoviedb.core.domain.model.MovieType
import com.imanancin.mymoviedb.data.source.remote.network.ApiResponse
import com.imanancin.mymoviedb.data.source.remote.network.ApiService
import com.imanancin.mymoviedb.data.source.remote.paging.MoviesPagingSource
import com.imanancin.mymoviedb.data.source.remote.response.MovieResponse
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

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
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

    // bisa pakai network bounds
    suspend fun getDetailMovie(id: String): Flow<ApiResponse<MovieResponse>> {
        return flow {
            try {
                val response = apiService.getMovie(id)
                emit(ApiResponse.Success(response))
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }


}