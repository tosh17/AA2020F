package ru.thstdio.aa2020.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.thstdio.aa2020.cache.convertors.CinemaIndexListTypeConverter
import ru.thstdio.aa2020.cache.dao.CinemaDao
import ru.thstdio.aa2020.cache.dao.CinemaDetailDao
import ru.thstdio.aa2020.cache.entity.*


@Database(
    entities = [CinemaEntity::class, CinemaDetailEntity::class,
        CinemasGenreEntity::class, GenreEntity::class, CinemasActorEntity::class, ActorEntity::class],
    version = AppDbContract.DATABASE_VERSION
)
@TypeConverters(CinemaIndexListTypeConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract val cinemaDao: CinemaDao
    abstract val cinemaDetailDao: CinemaDetailDao

    companion object {
        fun create(applicationContext: Context): AppDb = Room.databaseBuilder(
            applicationContext,
            AppDb::class.java,
            AppDbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}