package ru.thstdio.aa2020.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.aa2020.data.Actor

@Entity(tableName = "actor")
data class ActorEntity(
    @PrimaryKey()
    @ColumnInfo(name = "actor_id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "picture")
    val picture: String
)

fun Actor.toActorEntity(): ActorEntity = ActorEntity(
    id = this.id,
    name = this.name,
    picture = this.picture
)

fun ActorEntity.toActors(): Actor = Actor(
    id = this.id,
    name = this.name,
    picture = this.picture
)