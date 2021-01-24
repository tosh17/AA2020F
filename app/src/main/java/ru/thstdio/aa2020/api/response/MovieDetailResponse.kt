package ru.thstdio.aa2020.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.thstdio.aa2020.api.converter.adultToAge
import ru.thstdio.aa2020.api.converter.createOriginalImgUrl
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.CinemaDetail

@Serializable
data class MovieDetailResponse(
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("budget")
    val budget: Long,
    @SerialName("genres")
    val genres: List<GenreJson>,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("id")
    val id: Long,

    @SerialName("imdb_id")
    val imdbID: String,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String,


    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("revenue")
    val revenue: Long,
    @SerialName("runtime")
    val runtime: Int,

    @SerialName("status")
    val status: String,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("title")
    val title: String,
    @SerialName("video")
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int
)

fun MovieDetailResponse.toCinemaDetail(
    configuration: ConfigurationResponse,
    actors: List<Actor>
): CinemaDetail =
    CinemaDetail(
        id = id,
        title = this.title,
        genres = this.genres.map { genreJson -> genreJson.toGenre() },
        actors = actors,
        runtime = this.runtime,
        ratings = this.voteAverage.toFloat(),
        numberOfRatings = this.voteCount,
        backdrop = createOriginalImgUrl(this.posterPath, this.backdropPath, configuration),
        minimumAge = this.adult.adultToAge(),
        overview = this.overview
    )


