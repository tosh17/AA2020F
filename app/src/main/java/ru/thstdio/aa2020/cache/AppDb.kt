package ru.thstdio.aa2020.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.thstdio.aa2020.cache.convertors.CinemaIndexListTypeConverter
import ru.thstdio.aa2020.cache.dao.ActorDao
import ru.thstdio.aa2020.cache.dao.CinemaDao
import ru.thstdio.aa2020.cache.dao.CinemaDetailDao
import ru.thstdio.aa2020.cache.dao.GenreDao
import ru.thstdio.aa2020.cache.entity.ActorDto
import ru.thstdio.aa2020.cache.entity.CinemaDetailDto
import ru.thstdio.aa2020.cache.entity.CinemaDto
import ru.thstdio.aa2020.cache.entity.GenreDto


@Database(
    entities = [CinemaDto::class, CinemaDetailDto::class, GenreDto::class, ActorDto::class],
    version = 1
)
@TypeConverters(CinemaIndexListTypeConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract val actorDao: ActorDao
    abstract val cinemaDao: CinemaDao
    abstract val genreDao: GenreDao
    abstract val cinemaDetailDto: CinemaDetailDao

    companion object {
        fun create(applicationContext: Context): AppDb = Room.databaseBuilder(
            applicationContext,
            AppDb::class.java,
            "AppDb"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}