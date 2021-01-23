package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.aa2020.data.CinemaDetail

@Entity(tableName = "cinema_detail")
data class CinemaDetailEntity(
    @PrimaryKey()
    @ColumnInfo(name = "cinema_id")
    val id: Long,
    @ColumnInfo(name = "backdrop")
    val backdrop: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "runtime")
    val runtime: Int,
    @ColumnInfo(name = "actors")
    val actors: List<Long>
)

fun CinemaDetail.toCinemaDetailEntity(): CinemaDetailEntity = CinemaDetailEntity(
    id = this.id,
    backdrop = this.backdrop,
    overview = this.overview,
    runtime = this.runtime,
    actors = this.actors.map { it.id },
)