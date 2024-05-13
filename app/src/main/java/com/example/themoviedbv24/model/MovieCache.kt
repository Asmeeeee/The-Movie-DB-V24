package com.example.themoviedbv24.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_movies")
data class MovieCache(

    @PrimaryKey
    var category: String = "topRated",

    var results: List<Movie> = listOf(),

)
