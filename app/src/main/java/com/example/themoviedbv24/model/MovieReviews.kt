package com.example.themoviedbv24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviews(

    @SerialName(value = "id")
    var id: Long = 0L,

    @SerialName(value = "content")
    var content: String,

    @SerialName(value = "url")
    var url: String
)