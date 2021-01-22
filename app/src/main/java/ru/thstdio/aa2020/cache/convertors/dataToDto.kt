package ru.thstdio.aa2020.cache.convertors

import ru.thstdio.aa2020.cache.entity.ActorDto
import ru.thstdio.aa2020.cache.entity.CinemaDetailDto
import ru.thstdio.aa2020.cache.entity.GenreDto
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.data.Genre

fun Genre.toGenreDto(): GenreDto = GenreDto(this.id, this.name)
fun Actor.toActorDto(): ActorDto = ActorDto(
    id = this.id,
    name = this.name,
    picture = this.picture
)

fun CinemaDetail.toCinemaDetailDto(): CinemaDetailDto = CinemaDetailDto(
    id = this.id,
    backdrop = this.backdrop,
    overview = this.overview,
    runtime = this.runtime,
    actors = this.actors.map { it.id },
)