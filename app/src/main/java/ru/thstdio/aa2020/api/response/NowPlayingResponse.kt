package ru.thstdio.aa2020.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.thstdio.aa2020.api.converter.createPreviewImgUrl
import ru.thstdio.aa2020.data.Cinema
import ru.thstdio.aa2020.data.Genre

@Serializable
data class NowPlayingResponse(
    @SerialName("dates")
    val dates: Dates,
    @SerialName("page")
    val page: Long,
    @SerialName("results")
    val results: List<CinemaItemResponse>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Long
)

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String
)

@Serializable
data class CinemaItemResponse(
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>,

    val id: Long,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String,

    val title: String,
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int
)

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


