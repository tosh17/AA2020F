package ru.thstdio.aa2020.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("genres")
    val genres: List<GenreJson>
)

@Serializable
data class GenreJson(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)