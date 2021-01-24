package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.aa2020.cache.AppDbContract
import ru.thstdio.aa2020.data.Genre

@Entity(tableName = AppDbContract.GenreEntity.TABLE_NAME)
data class GenreEntity(
    @PrimaryKey()
    @ColumnInfo(name = AppDbContract.GenreEntity.COLUMN_NAME_ID)
    val id: Long,
    @ColumnInfo(name = AppDbContract.GenreEntity.COLUMN_NAME_NAME)
    val name: String
)

fun GenreEntity.toCinemaGenre(idCinema: Long): CinemasGenreEntity =
    CinemasGenreEntity(cinemaId = idCinema, genreId = this.id)

fun Genre.toGenreEntity(): GenreEntity = GenreEntity(this.id, this.name)
fun GenreEntity.toGenre(): Genre = Genre(this.id, this.name)