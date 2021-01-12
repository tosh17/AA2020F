package ru.thstdio.aa2020.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("cast")
    val cast: List<Cast>
)

@Serializable
data class Cast(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("gender")
    val gender: Long,
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,

    @SerialName("original_name")
    val originalName: String,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("cast_id")
    val castID: Long? = null,

    @SerialName("credit_id")
    val creditID: String
)

