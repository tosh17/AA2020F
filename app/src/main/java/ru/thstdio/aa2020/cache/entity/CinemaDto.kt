package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinema")
data class CinemaDto(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "genres")
    val genres: List<Long>,
    @ColumnInfo(name = "ratings")
    val ratings: Float,
    @ColumnInfo(name = "numberOfRatings")
    val numberOfRatings: Int,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @ColumnInfo(name = "position")
    val position: Long,
)