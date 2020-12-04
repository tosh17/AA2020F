package ru.thstdio.aa2020.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CinemaDto(
    val id: Int,
    val name: String,
    val genre: String,
    val posterId: Int,
    val time: Int,
    val age: String,
    val rating: Int,
    val reviews: Int,
    val like: Boolean,
) : Parcelable