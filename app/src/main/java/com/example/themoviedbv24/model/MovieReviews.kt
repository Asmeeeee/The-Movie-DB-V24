package com.example.themoviedbv24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviews(

    @SerialName(value = "id")
    var id: String,

    @SerialName(value = "content")
    var content: String,

    @SerialName(value = "url")
    var url: String,

    @SerialName(value = "updated_at")
    var updated_at: String
)