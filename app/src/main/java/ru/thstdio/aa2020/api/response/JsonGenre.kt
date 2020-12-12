package ru.thstdio.aa2020.api.response

import kotlinx.serialization.Serializable
import ru.thstdio.aa2020.data.Genre

@Serializable
class JsonGenre(val id: Long, val name: String)

fun JsonGenre.toGenre() = Genre(id = this.id, name = this.name)
