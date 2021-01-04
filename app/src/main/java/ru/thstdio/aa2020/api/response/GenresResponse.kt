package ru.thstdio.aa2020.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("genres")
    val genres: List<Genre>
)

@Serializable
data class Genre(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)