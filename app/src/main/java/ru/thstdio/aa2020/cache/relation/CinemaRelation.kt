package ru.thstdio.aa2020.cache.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.thstdio.aa2020.cache.entity.CinemaEntity
import ru.thstdio.aa2020.cache.entity.CinemasGenreEntity
import ru.thstdio.aa2020.cache.entity.GenreEntity
import ru.thstdio.aa2020.cache.entity.toGenre
import ru.thstdio.aa2020.data.Cinema

data class CinemaRelation(
    @Embedded
    val cinema: CinemaEntity,
    @Relation(
        parentColumn = "cinema_id",
        entityColumn = "genre_id",
        associateBy = Junction(CinemasGenreEntity::class)
    )
    val genres: List<GenreEntity>
)

fun CinemaRelation.toCinema(): Cinema = Cinema(
    id = this.cinema.id,
    title = this.cinema.title,
    poster = this.cinema.poster,
    genres = this.genres.map { it.toGenre() },
    ratings = this.cinema.ratings,
    numberOfRatings = this.cinema.numberOfRatings,
    adult = this.cinema.adult
)