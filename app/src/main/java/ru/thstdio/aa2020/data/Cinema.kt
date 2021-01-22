package ru.thstdio.aa2020.data

data class Cinema(
    val id: Long,
    val title: String,
    val poster: String,
    val genres: List<Genre>,
    val ratings: Float,
    val numberOfRatings: Int,
    val adult: Boolean
)