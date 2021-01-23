package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "cinema_genre",
    primaryKeys = ["cinema_id", "genre_id"],
    foreignKeys = [ForeignKey(
        entity = CinemaEntity::class,
        parentColumns = arrayOf("cinema_id"),
        childColumns = arrayOf("cinema_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
class CinemasGenreEntity(
    @ColumnInfo(name = "cinema_id")
    val idCinema: Long,
    @ColumnInfo(name = "genre_id")
    val idGenre: Long
)

