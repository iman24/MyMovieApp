package com.imanancin.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class MovieEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "release")
    val release: String,

    @ColumnInfo(name = "vote")
    val vote: Double,

    @ColumnInfo(name = "poster")
    val poster: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "backdrop")
    val backdrop: String?



)