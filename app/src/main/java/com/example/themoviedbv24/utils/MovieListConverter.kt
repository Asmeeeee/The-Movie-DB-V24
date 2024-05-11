package com.example.themoviedbv24.utils

import androidx.room.TypeConverter
import com.example.themoviedbv24.model.Movie
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MovieListConverter {
    private val json = Json {  }

    @TypeConverter
    fun fromString(value: String): List<Movie> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun toString(list: List<Movie>): String {
        return json.encodeToString(list)
    }
}