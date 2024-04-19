package com.example.themoviedbv24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieVideo (

    @SerialName(value = "id")
    var id: String,

    @SerialName(value = "official")
    var official: Boolean,

    @SerialName(value = "site")
    var site: String,

    @SerialName(value = "name")
    var name: String,

    @SerialName(value = "type")
    var type: String,

    @SerialName(value = "size")
    var size: Long,

    @SerialName(value = "key")
    var key: String,

    @SerialName(value = "published_at")
    var published_at: String,
)