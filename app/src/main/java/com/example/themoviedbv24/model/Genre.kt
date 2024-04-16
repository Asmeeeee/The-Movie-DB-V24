package com.example.themoviedbv24.model

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    var id: Long = 0L,
    var name: String
)