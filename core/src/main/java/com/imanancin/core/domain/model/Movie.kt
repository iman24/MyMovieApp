package com.imanancin.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val release: String,
    val vote: Double,
    val poster: String,
    val overview: String,
    val genre: String,
    val backdrop: String?
) : Parcelable