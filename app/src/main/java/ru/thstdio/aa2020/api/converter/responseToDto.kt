package ru.thstdio.aa2020.api.converter

import ru.thstdio.aa2020.api.response.CinemaItemResponse
import ru.thstdio.aa2020.api.response.ConfigurationResponse
import ru.thstdio.aa2020.cache.entity.CinemaDto

fun CinemaItemResponse.toCinemaDto(
    configuration: ConfigurationResponse, position: Long
): CinemaDto = CinemaDto(
    id = this.id,
    title = this.title,
    poster = createPreviewImgUrl(this.posterPath, this.backdropPath, configuration),
    genres = this.genreIDS,
    ratings = this.voteAverage.toFloat(),
    numberOfRatings = this.voteCount,
    adult = this.adult,
    position = position
)