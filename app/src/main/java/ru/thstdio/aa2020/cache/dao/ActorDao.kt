package ru.thstdio.aa2020.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.thstdio.aa2020.cache.entity.ActorDto

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listActorDto: List<ActorDto>)
}