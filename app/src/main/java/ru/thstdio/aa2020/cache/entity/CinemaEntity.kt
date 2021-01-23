package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.aa2020.api.converter.createPreviewImgUrl
import ru.thstdio.aa2020.api.response.CinemaItemResponse
import ru.thstdio.aa2020.api.response.ConfigurationResponse
import ru.thstdio.aa2020.cache.relation.CinemaRelation
import ru.thstdio.aa2020.data.Genre

@Entity(tableName = "cinema")
data class CinemaEntity(
    @PrimaryKey()
    @ColumnInfo(name = "cinema_id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "ratings")
    val ratings: Float,
    @ColumnInfo(name = "numberOfRatings")
    val numberOfRatings: Int,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @ColumnInfo(name = "position")
    val position: Long,
)

fun CinemaItemResponse.toCinemaEntity(
    configuration: ConfigurationResponse, genres: Map<Long, Genre>, position: Long
): CinemaRelation = CinemaRelation(
    cinema = CinemaEntity(
        id = this.id,
        title = this.title,
        poster = createPreviewImgUrl(this.posterPath, this.backdropPath, configuration),
        ratings = this.voteAverage.toFloat(),
        numberOfRatings = this.voteCount,
        adult = this.adult,
        position = position
    ),
    genres = genreIDS.mapNotNull { genres[it]?.toGenreEntity() }
)