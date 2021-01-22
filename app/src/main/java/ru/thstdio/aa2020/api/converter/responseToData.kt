package ru.thstdio.aa2020.api.converter

import ru.thstdio.aa2020.api.response.*
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.Cinema
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.data.Genre

fun CinemaItemResponse.toCinema(
    configuration: ConfigurationResponse,
    genresMap: Map<Long, Genre>
): Cinema =
    Cinema(
        id = this.id,
        title = this.title,
        poster = createPreviewImgUrl(this.posterPath, this.backdropPath, configuration),
        genres = this.genreIDS.mapNotNull { id -> genresMap[id] },
        ratings = this.voteAverage.toFloat(),
        numberOfRatings = this.voteCount,
        adult = this.adult
    )

fun createPreviewImgUrl(
    posterPath: String?, backdropPath: String?,
    configuration: ConfigurationResponse
): String =
    when {
        backdropPath != null -> configuration.images.secureBaseURL + configuration.images.backdropSizes.first() + backdropPath
        posterPath != null -> configuration.images.secureBaseURL + configuration.images.posterSizes.first() + posterPath
        else -> ""
    }

fun MovieDetailResponse.toCinemaDetail(
    configuration: ConfigurationResponse,
    actors: List<Actor>
): CinemaDetail =
    CinemaDetail(
        id = id,
        title = this.title,
        genres = this.genres.map { it.toGenre() },
        actors = actors,
        runtime = this.runtime,
        ratings = this.voteAverage.toFloat(),
        numberOfRatings = this.voteCount,
        backdrop = createOriginalImgUrl(this.posterPath, this.backdropPath, configuration),
        minimumAge = this.adult.adultToAge(),
        overview = this.overview
    )

fun Cast.toActor(configuration: ConfigurationResponse): Actor = Actor(
    id = this.id,
    name = this.name,
    picture = configuration.images.secureBaseURL + configuration.images.backdropSizes.last()
            + this.profilePath
)

fun createOriginalImgUrl(
    posterPath: String?, backdropPath: String?,
    configuration: ConfigurationResponse
): String =
    when {
        backdropPath != null -> configuration.images.secureBaseURL + configuration.images.backdropSizes.last() + backdropPath
        posterPath != null -> configuration.images.secureBaseURL + configuration.images.posterSizes.last() + posterPath
        else -> ""
    }

fun Boolean.adultToAge(): Int = if (this) 18 else 6

fun GenreJson.toGenre(): Genre {
    return Genre(this.id, this.name)
}