package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import ru.thstdio.aa2020.cache.AppDbContract

@Entity(
    tableName = AppDbContract.CinemasGenreEntity.TABLE_NAME,
    primaryKeys = [AppDbContract.CinemasGenreEntity.COLUMN_NAME_CINEMA_ID,
        AppDbContract.CinemasGenreEntity.COLUMN_NAME_GENRE_ID],
    foreignKeys = [ForeignKey(
        entity = CinemaEntity::class,
        parentColumns = arrayOf(AppDbContract.CinemaEntity.COLUMN_NAME_ID),
        childColumns = arrayOf(AppDbContract.CinemasGenreEntity.COLUMN_NAME_CINEMA_ID),
        onDelete = ForeignKey.CASCADE
    )]
)
class CinemasGenreEntity(
    @ColumnInfo(name = AppDbContract.CinemasGenreEntity.COLUMN_NAME_CINEMA_ID)
    val cinemaId: Long,
    @ColumnInfo(name = AppDbContract.CinemasGenreEntity.COLUMN_NAME_GENRE_ID)
    val genreId: Long
)

