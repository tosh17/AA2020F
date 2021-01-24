package ru.thstdio.aa2020.cache.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.thstdio.aa2020.api.converter.adultToAge
import ru.thstdio.aa2020.cache.AppDbContract
import ru.thstdio.aa2020.cache.entity.*
import ru.thstdio.aa2020.data.CinemaDetail

data class CinemaDetailRelation(
    @Embedded
    val cinema: CinemaEntity,
    @Relation(
        parentColumn = AppDbContract.CinemaEntity.COLUMN_NAME_ID,
        entityColumn = AppDbContract.CinemaDetailEntity.COLUMN_NAME_ID,
        entity = CinemaDetailEntity::class
    )
    val cinemaDetail: CinemaDetailEntity,

    @Relation(
        parentColumn = AppDbContract.CinemaEntity.COLUMN_NAME_ID,
        entityColumn = AppDbContract.GenreEntity.COLUMN_NAME_ID,
        associateBy = Junction(CinemasGenreEntity::class)
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = AppDbContract.CinemaEntity.COLUMN_NAME_ID,
        entityColumn = AppDbContract.ActorEntity.COLUMN_NAME_ID,
        associateBy = Junction(CinemasActorEntity::class)
    )
    val actors: List<ActorEntity>
)

fun CinemaDetailRelation.toCinemaDetail(): CinemaDetail = CinemaDetail(
    id = this.cinema.id,
    title = this.cinema.title,
    genres = this.genres.map { it.toGenre() },
    actors = this.actors.map { it.toActor() },
    runtime = this.cinemaDetail.runtime,
    ratings = this.cinema.ratings.toFloat(),
    numberOfRatings = this.cinema.numberOfRatings,
    backdrop = this.cinemaDetail.backdrop,
    minimumAge = this.cinema.adult.adultToAge(),
    overview = this.cinemaDetail.overview
)

