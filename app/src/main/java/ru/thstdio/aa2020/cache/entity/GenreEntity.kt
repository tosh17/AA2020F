package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.aa2020.data.Genre

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey()
    @ColumnInfo(name = "genre_id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String
)

fun GenreEntity.toCinemaGenre(idCinema: Long): CinemasGenreEntity =
    CinemasGenreEntity(idCinema = idCinema, idGenre = this.id)

fun Genre.toGenreEntity(): GenreEntity = GenreEntity(this.id, this.name)
fun GenreEntity.toGenre(): Genre = Genre(this.id, this.name)