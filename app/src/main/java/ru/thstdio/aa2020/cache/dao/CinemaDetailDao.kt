package ru.thstdio.aa2020.cache.dao

import androidx.room.*
import ru.thstdio.aa2020.cache.AppDbContract
import ru.thstdio.aa2020.cache.entity.ActorEntity
import ru.thstdio.aa2020.cache.entity.CinemaDetailEntity
import ru.thstdio.aa2020.cache.entity.CinemasActorEntity
import ru.thstdio.aa2020.cache.relation.CinemaDetailRelation

@Dao
interface CinemaDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cinema: CinemaDetailEntity)

    @Transaction
    @Query("SELECT * FROM ${AppDbContract.CinemaEntity.TABLE_NAME} Where ${AppDbContract.CinemaEntity.COLUMN_NAME_ID} = :id")
    suspend fun getCinemaDetailRelation(id: Long): CinemaDetailRelation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(listActorDto: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCinemaActors(map: List<CinemasActorEntity>)

    @Query("SELECT ${AppDbContract.CinemaDetailEntity.COLUMN_NAME_ID} FROM ${AppDbContract.CinemaDetailEntity.TABLE_NAME}")
    suspend fun getCinemaDetailIDs(): List<Long>
}