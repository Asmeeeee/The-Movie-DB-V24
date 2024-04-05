package com.example.themoviedbv24.model

import androidx.annotation.StringRes

data class Movie(
    var id: Long = 0L,
    var title: String,
    var posterPath: String,
    var backdropPath: String,
    var releaseDate: String,
    var overview: String
)
