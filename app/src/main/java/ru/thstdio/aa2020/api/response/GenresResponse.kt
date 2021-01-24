package ru.thstdio.aa2020.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.thstdio.aa2020.data.Genre

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

fun GenreJson.toGenre(): Genre = Genre(this.id, this.name)
