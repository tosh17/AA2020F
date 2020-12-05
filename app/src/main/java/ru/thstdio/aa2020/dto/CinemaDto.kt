package ru.thstdio.aa2020.dto

import androidx.annotation.DrawableRes

data class CinemaDto(
    val id: Int,
    val name: String,
    val genre: String,
    @DrawableRes val posterId: Int,
    val time: Int,
    val age: String,
    val rating: Int,
    val reviews: Int,
    val like: Boolean,
)