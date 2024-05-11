package com.example.themoviedbv24.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "cache_movies")
@Serializable
data class MovieResponse(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @SerialName(value = "page")
    var page: Int = 0,

    @SerialName(value = "results")
    var results: List<Movie> = listOf(),

    @SerialName(value = "total_pages")
    var total_pages: Int = 0,

    @SerialName(value = "total_results")
    var total_results: Int = 0,
)