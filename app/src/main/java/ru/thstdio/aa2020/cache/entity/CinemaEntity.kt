package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.aa2020.api.converter.createPreviewImgUrl
import ru.thstdio.aa2020.api.response.CinemaItemResponse
import ru.thstdio.aa2020.api.response.ConfigurationResponse
import ru.thstdio.aa2020.cache.AppDbContract
import ru.thstdio.aa2020.cache.relation.CinemaRelation
import ru.thstdio.aa2020.data.Genre

@Entity(tableName = AppDbContract.CinemaEntity.TABLE_NAME)
data class CinemaEntity(
    @PrimaryKey()
    @ColumnInfo(name = AppDbContract.CinemaEntity.COLUMN_NAME_ID)
    val id: Long,
    @ColumnInfo(name = AppDbContract.CinemaEntity.COLUMN_NAME_TITLE)
    val title: String,
    @ColumnInfo(name = AppDbContract.CinemaEntity.COLUMN_NAME_POSTER)
    val poster: String,
    @ColumnInfo(name = AppDbContract.CinemaEntity.COLUMN_NAME_RATING)
    val ratings: Float,
    @ColumnInfo(name = AppDbContract.CinemaEntity.COLUMN_NAME_NUMBER_OF_RATINGS)
    val numberOfRatings: Int,
    @ColumnInfo(name = AppDbContract.CinemaEntity.COLUMN_NAME_ADULT)
    val adult: Boolean,
    @ColumnInfo(name = AppDbContract.CinemaEntity.COLUMN_NAME_POSITION)
    val position: Long,
)


fun CinemaItemResponse.toCinemaEntity(
    configuration: ConfigurationResponse,
    genres: Map<Long, Genre>,
    position: Long
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