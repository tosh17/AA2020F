package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import ru.thstdio.aa2020.cache.AppDbContract
import ru.thstdio.aa2020.data.Actor

@Entity(
    tableName = AppDbContract.CinemasActorEntity.TABLE_NAME,
    primaryKeys = [AppDbContract.CinemasActorEntity.COLUMN_NAME_CINEMA_ID,
        AppDbContract.CinemasActorEntity.COLUMN_NAME_ACTOR_ID],
    foreignKeys = [ForeignKey(
        entity = CinemaEntity::class,
        parentColumns = arrayOf(AppDbContract.CinemaEntity.COLUMN_NAME_ID),
        childColumns = arrayOf(AppDbContract.CinemasActorEntity.COLUMN_NAME_CINEMA_ID),
        onDelete = ForeignKey.CASCADE
    )]
)
class CinemasActorEntity(
    @ColumnInfo(name = AppDbContract.CinemasActorEntity.COLUMN_NAME_CINEMA_ID)
    val cinemaId: Long,
    @ColumnInfo(name = AppDbContract.CinemasActorEntity.COLUMN_NAME_ACTOR_ID)
    val actorId: Long
)

fun Actor.toCinemaActorEntity(id: Long): CinemasActorEntity =
    CinemasActorEntity(cinemaId = id, actorId = this.id)

