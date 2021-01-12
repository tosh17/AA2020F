package ru.thstdio.aa2020.data

import ru.thstdio.aa2020.api.response.Genre

data class CinemaDetail(
    val id: Long,
    val title: String,
    val overview: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor>
)