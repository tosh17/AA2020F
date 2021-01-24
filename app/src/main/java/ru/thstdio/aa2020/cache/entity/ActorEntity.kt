package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.aa2020.cache.AppDbContract
import ru.thstdio.aa2020.data.Actor

@Entity(tableName = AppDbContract.ActorEntity.TABLE_NAME)
data class ActorEntity(
    @PrimaryKey()
    @ColumnInfo(name = AppDbContract.ActorEntity.COLUMN_NAME_ID)
    val id: Long,
    @ColumnInfo(name = AppDbContract.ActorEntity.COLUMN_NAME_NAME)
    val name: String,
    @ColumnInfo(name = AppDbContract.ActorEntity.COLUMN_NAME_PICTURE)
    val picture: String
)

fun Actor.toActorEntity(): ActorEntity = ActorEntity(
    id = this.id,
    name = this.name,
    picture = this.picture
)

fun ActorEntity.toActor(): Actor = Actor(
    id = this.id,
    name = this.name,
    picture = this.picture
)