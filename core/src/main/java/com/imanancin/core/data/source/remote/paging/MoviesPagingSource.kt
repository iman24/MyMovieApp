package com.imanancin.core.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.imanancin.core.data.source.remote.network.ApiService
import com.imanancin.core.data.source.remote.response.ListMovieResponse
import com.imanancin.core.data.source.remote.response.MovieResponse
import com.imanancin.core.domain.model.MovieType
import retrofit2.HttpException

class MoviesPagingSource(val apiService: ApiService, val movieType: MovieType) : PagingSource<Int, MovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pageIndex = params.key ?: DEFAULT_PAGE
        return try {

            val response: ListMovieResponse =  when(movieType) {
                MovieType.NOW_PLAYING -> apiService.getNowPlayingMovies(page = pageIndex)
                MovieType.TOP_RATED -> apiService.getTopRatedMovies(page = pageIndex)
                MovieType.POPULAR -> apiService.getPopularMovies(page = pageIndex)
                MovieType.UPCOMING -> apiService.getUpComingMovies(page = pageIndex)
            }

            val movies = response.results
            val next = if(movies.isEmpty()) {
                null
            } else {
                pageIndex + 1
            }

            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == DEFAULT_PAGE) null else pageIndex,
                nextKey = next
            )
        }catch (e: Exception) {
            return LoadResult.Error(e)
        }catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_PAGE = 1
    }

}
