package ru.thstdio.aa2020.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.thstdio.aa2020.data.Actor

@Serializable
class JsonActor(
    val id: Long,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)

fun JsonActor.toActor(): Actor =
    Actor(id = this.id, name = this.name, picture = this.profilePicture)
