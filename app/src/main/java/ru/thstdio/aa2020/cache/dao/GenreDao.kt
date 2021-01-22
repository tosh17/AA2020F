package ru.thstdio.aa2020.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.thstdio.aa2020.cache.entity.GenreDto

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre WHERE id = :id")
    suspend fun getGenre(id: Long): GenreDto

    @Query("SELECT * FROM genre")
    suspend fun getAll(): List<GenreDto>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(map: List<GenreDto>)
}