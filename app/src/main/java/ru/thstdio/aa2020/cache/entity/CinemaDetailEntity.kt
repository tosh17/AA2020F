package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.aa2020.cache.AppDbContract
import ru.thstdio.aa2020.data.CinemaDetail

@Entity(tableName = AppDbContract.CinemaDetailEntity.TABLE_NAME)
data class CinemaDetailEntity(
    @PrimaryKey
    @ColumnInfo(name = AppDbContract.CinemaDetailEntity.COLUMN_NAME_ID)
    val id: Long,
    @ColumnInfo(name = AppDbContract.CinemaDetailEntity.COLUMN_NAME_BACKDROP)
    val backdrop: String,
    @ColumnInfo(name = AppDbContract.CinemaDetailEntity.COLUMN_NAME_OVERVIEW)
    val overview: String,
    @ColumnInfo(name = AppDbContract.CinemaDetailEntity.COLUMN_NAME_RUNTIME)
    val runtime: Int
)

fun CinemaDetail.toCinemaDetailEntity(): CinemaDetailEntity = CinemaDetailEntity(
    id = this.id,
    backdrop = this.backdrop,
    overview = this.overview,
    runtime = this.runtime,

    )