package com.example.themoviedbv24.model

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoResponse (
    @SerialName(value = "page")
    var page: Int = 0,

    @SerialName(value = "results")
    var results: List<MovieVideo> = listOf(),

    @SerialName(value = "total_pages")
    var total_pages: Int = 0,

    @SerialName(value = "total_results")
    var total_results: Int = 0,
)