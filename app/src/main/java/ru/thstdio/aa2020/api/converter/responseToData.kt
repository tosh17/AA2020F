package ru.thstdio.aa2020.api.converter

import ru.thstdio.aa2020.api.response.*
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.Cinema
import ru.thstdio.aa2020.data.CinemaDetail

fun CinemaItemResponse.toCinema(config: ConfigResponse, genresMap: Map<Long, Genre>) = Cinema(
    id = this.id,
    title = this.title,
    poster = createPreviewImgUrl(this.posterPath, this.backdropPath, config),
    genres = this.genreIDS.mapNotNull { id -> genresMap[id] },
    ratings = this.voteAverage.toFloat(),
    numberOfRatings = this.voteCount,
    adult = this.adult
)

fun createPreviewImgUrl(
    posterPath: String?, backdropPath: String?,
    config: ConfigResponse
): String =
    when {
        backdropPath != null -> config.images.secureBaseURL + config.images.backdropSizes.first() + backdropPath
        posterPath != null -> config.images.secureBaseURL + config.images.posterSizes.first() + posterPath
        else -> ""
    }

fun MovieDetailResponse.toCinemaDetail(config: ConfigResponse, actors: List<Actor>): CinemaDetail =
    CinemaDetail(
        id = id,
        title = this.title,
        genres = this.genres,
        actors = actors,
        runtime = this.runtime,
        ratings = this.voteAverage.toFloat(),
        numberOfRatings = this.voteCount,
        backdrop = createOriginalImgUrl(this.posterPath, this.backdropPath, config),
        minimumAge = if (this.adult) 18 else 6,
        overview = this.overview
    )

fun Cast.toActor(config: ConfigResponse): Actor = Actor(
    id = this.id,
    name = this.name,
    picture = config.images.secureBaseURL + config.images.backdropSizes.last()
            + this.profilePath
)

fun createOriginalImgUrl(
    posterPath: String?, backdropPath: String?,
    config: ConfigResponse
): String =
    when {
        backdropPath != null -> config.images.secureBaseURL + config.images.backdropSizes.last() + backdropPath
        posterPath != null -> config.images.secureBaseURL + config.images.posterSizes.last() + posterPath
        else -> ""
    }

