package ru.thstdio.aa2020.cache.convertors

import ru.thstdio.aa2020.api.converter.adultToAge
import ru.thstdio.aa2020.cache.entity.ActorDto
import ru.thstdio.aa2020.cache.entity.CinemaDetailDto
import ru.thstdio.aa2020.cache.entity.CinemaDto
import ru.thstdio.aa2020.cache.entity.GenreDto
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.Cinema
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.data.Genre

fun GenreDto.toGenre(): Genre = Genre(this.id, this.name)

fun ActorDto.toActors(): Actor = Actor(
    id = this.id,
    name = this.name,
    picture = this.picture
)

fun CinemaDto.toCinema(genres: Map<Long, Genre>): Cinema = Cinema(
    id = this.id,
    title = this.title,
    poster = this.poster,
    genres = this.genres.mapNotNull { id -> genres[id] },
    ratings = this.ratings,
    numberOfRatings = this.numberOfRatings,
    adult = this.adult
)

fun createCinemaDetail(
    cinema: CinemaDto,
    cinemaDetail: CinemaDetailDto,
    actors: List<ActorDto>,
    genres: List<GenreDto>
): CinemaDetail = CinemaDetail(
    id = cinema.id,
    title = cinema.title,
    overview = cinemaDetail.overview,
    backdrop = cinemaDetail.backdrop,
    ratings = cinema.ratings,
    numberOfRatings = cinema.numberOfRatings,
    minimumAge = cinema.adult.adultToAge(),
    runtime = cinemaDetail.runtime,
    genres = genres.map { it.toGenre() },
    actors = actors.map { it.toActors() })