package ru.thstdio.aa2020.cache.dao

import androidx.room.*
import ru.thstdio.aa2020.cache.AppDbContract
import ru.thstdio.aa2020.cache.entity.CinemaEntity
import ru.thstdio.aa2020.cache.entity.CinemasGenreEntity
import ru.thstdio.aa2020.cache.entity.GenreEntity
import ru.thstdio.aa2020.cache.entity.toCinemaGenre
import ru.thstdio.aa2020.cache.relation.CinemaRelation

@Dao
interface CinemaDao {
    @Transaction
    suspend fun insertAll(list: List<CinemaRelation>) {
        insertCinemas(list.map { cinemaRelation -> cinemaRelation.cinema })
        val listCinemaGenre: List<CinemasGenreEntity> = list
            .map { cinemaRelation ->
                cinemaRelation.genres.map { genre ->
                    genre.toCinemaGenre(cinemaRelation.cinema.id)
                }
            }.flatten()
        insertCinemaGenres(listCinemaGenre)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCinemas(cinemas: List<CinemaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCinemaGenres(cinemas: List<CinemasGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(cinemas: List<GenreEntity>)

    @Transaction
    @Query("SELECT * FROM ${AppDbContract.CinemaEntity.TABLE_NAME}  Order by ${AppDbContract.CinemaEntity.COLUMN_NAME_POSITION} ASC")
    suspend fun getCinemas(): List<CinemaRelation>

}