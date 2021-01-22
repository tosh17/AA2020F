package ru.thstdio.aa2020.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.thstdio.aa2020.cache.entity.CinemaDto

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cinema: CinemaDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listCinemaDto: List<CinemaDto>)

    @Query("SELECT * FROM cinema WHERE id = :id")
    suspend fun getCinema(id: Long): CinemaDto


    @Query("SELECT * FROM cinema")
    suspend fun getAll(): List<CinemaDto>

}