package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinema_detail")
data class CinemaDetailDto(
    @PrimaryKey()
    @ColumnInfo(name = "id")
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