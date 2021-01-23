package ru.thstdio.aa2020.cache.dao

import androidx.room.*
import ru.thstdio.aa2020.cache.entity.ActorEntity
import ru.thstdio.aa2020.cache.entity.CinemaDetailEntity
import ru.thstdio.aa2020.cache.entity.CinemasActorEntity
import ru.thstdio.aa2020.cache.relation.CinemaDetailRelation

@Dao
interface CinemaDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cinema: CinemaDetailEntity)

    @Transaction
    @Query("SELECT * FROM cinema Where cinema_id = :id")
    suspend fun getMovieDetail(id: Long): CinemaDetailRelation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActorAll(listActorDto: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCinemaActorAll(map: List<CinemasActorEntity>)
}