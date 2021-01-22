package ru.thstdio.aa2020.cache.dao

import androidx.room.*
import ru.thstdio.aa2020.cache.convertors.createCinemaDetail
import ru.thstdio.aa2020.cache.entity.ActorDto
import ru.thstdio.aa2020.cache.entity.CinemaDetailDto
import ru.thstdio.aa2020.cache.entity.CinemaDto
import ru.thstdio.aa2020.cache.entity.GenreDto
import ru.thstdio.aa2020.data.CinemaDetail

@Dao
interface CinemaDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cinema: CinemaDetailDto)

    @Transaction
    suspend fun getMovieDetail(id: Long): CinemaDetail {
        val cinema = getCinema(id)
        val cinemaDetail = getCinemaDetail(id)
        val actors = getActors(cinemaDetail.actors)
        val genres = getGenres(cinema.genres)
        return createCinemaDetail(cinema, cinemaDetail, actors, genres)
    }

    @Query("SELECT * FROM cinema Where id = :id")
    suspend fun getCinema(id: Long): CinemaDto

    @Query("SELECT * FROM cinema_detail Where id = :id")
    suspend fun getCinemaDetail(id: Long): CinemaDetailDto

    @Query("SELECT * FROM actor Where id in (:indexes)")
    suspend fun getActors(indexes: List<Long>): List<ActorDto>

    @Query("SELECT * FROM genre Where id in (:indexes)")
    suspend fun getGenres(indexes: List<Long>): List<GenreDto>
}