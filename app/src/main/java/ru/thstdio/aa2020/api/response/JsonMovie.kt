package ru.thstdio.aa2020.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.Genre
import ru.thstdio.aa2020.data.Movie

@Serializable
class JsonMovie(
    val id: Long,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Long>,
    val actors: List<Long>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    val overview: String,
    val adult: Boolean
)

fun JsonMovie.toMovie(actorsMap: Map<Long, Actor>, genresMap: Map<Long, Genre>): Movie =
    Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        poster = this.posterPicture,
        backdrop = this.backdropPicture,
        ratings = this.ratings,
        numberOfRatings = this.votesCount,
        minimumAge = if (this.adult) 16 else 13,
        runtime = this.runtime,
        genres = this.genreIds.map {
            genresMap[it] ?: throw IllegalArgumentException("Genre not found")
        },
        actors = this.actors.map {
            actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
        }

    )