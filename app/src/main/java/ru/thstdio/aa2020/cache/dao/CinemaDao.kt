package ru.thstdio.aa2020.cache.dao

import androidx.room.*
import ru.thstdio.aa2020.cache.entity.CinemaEntity
import ru.thstdio.aa2020.cache.entity.CinemasGenreEntity
import ru.thstdio.aa2020.cache.entity.GenreEntity
import ru.thstdio.aa2020.cache.entity.toCinemaGenre
import ru.thstdio.aa2020.cache.relation.CinemaRelation

@Dao
interface CinemaDao {
    @Transaction
    suspend fun insertAll(list: List<CinemaRelation>) {
        insertCinemaAll(list.map { it.cinema })
        insertCinemaGenresAll(
            list.map { cinema ->
                cinema.genres.map { genre ->
                    genre.toCinemaGenre(cinema.cinema.id)
                }
            }.flatten()
        )
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCinemaAll(cinemas: List<CinemaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCinemaGenresAll(cinemas: List<CinemasGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresAll(cinemas: List<GenreEntity>)

    @Transaction
    @Query("SELECT * FROM cinema")
    suspend fun getCinemaAll(): List<CinemaRelation>

}