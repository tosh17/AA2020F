package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import ru.thstdio.aa2020.data.Actor

@Entity(
    tableName = "cinema_actor",
    primaryKeys = ["cinema_id", "actor_id"],
    foreignKeys = [ForeignKey(
        entity = CinemaEntity::class,
        parentColumns = arrayOf("cinema_id"),
        childColumns = arrayOf("cinema_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
class CinemasActorEntity(
    @ColumnInfo(name = "cinema_id")
    val idCinema: Long,
    @ColumnInfo(name = "actor_id")
    val idActor: Long
)

fun Actor.toCinemaActorEntity(id: Long): CinemasActorEntity =
    CinemasActorEntity(idCinema = id, idActor = this.id)

