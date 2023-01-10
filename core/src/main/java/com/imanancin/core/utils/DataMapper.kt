package com.imanancin.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.imanancin.core.data.source.local.entity.MovieEntity
import com.imanancin.core.data.source.remote.response.MovieResponse
import com.imanancin.core.domain.model.Movie

object DataMapper {

    fun mapPagingToDomain(list: PagingData<MovieResponse>) : PagingData<Movie> {
        return list.map {
            Movie(
                id = it.id.toString(),
                title = it.title,
                release = it.releaseDate,
                vote = it.voteAverage,
                poster = it.posterPath,
                overview = it.overview,
                backdrop = it.backdropPath,
                genre = it.genreIds.map { genre -> idGenreFormatted(genre) }.toList().joinToString(",")
            )
        }
    }

    fun mapPagingEntityToDomain(list: PagingData<MovieEntity>) : PagingData<Movie> {

        return list.map {
            Movie(
                id = it.id,
                title = it.title,
                release = it.release,
                vote = it.vote,
                poster = it.poster,
                overview = it.overview,
                backdrop = it.backdrop,
                genre = it.genre
            )
        }

    }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        release = input.release,
        vote = input.vote,
        poster = input.poster,
        overview = input.overview,
        backdrop = input.backdrop,
        genre = input.genre
    )


    private fun idGenreFormatted(id: Int): String? {
        val genreId = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
        )

        return genreId[id]
    }




}