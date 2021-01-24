package ru.thstdio.aa2020.cache

object AppDbContract {
    const val DATABASE_NAME = "app_db.db"

    object ActorEntity {
        const val TABLE_NAME = "actor"

        const val COLUMN_NAME_ID = "actor_id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_PICTURE = "picture"
    }

    object CinemaDetailEntity {
        const val TABLE_NAME = "cinema_detail"

        const val COLUMN_NAME_ID = "cinema_id"
        const val COLUMN_NAME_BACKDROP = "backdrop"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_RUNTIME = "runtime"
    }

    object CinemaEntity {
        const val TABLE_NAME = "cinema"

        const val COLUMN_NAME_ID = "cinema_id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_POSTER = "poster"
        const val COLUMN_NAME_RATING = "ratings"
        const val COLUMN_NAME_NUMBER_OF_RATINGS = "numberOfRatings"
        const val COLUMN_NAME_ADULT = "adult"
        const val COLUMN_NAME_POSITION = "position"
    }

    object GenreEntity {
        const val TABLE_NAME = "genre"

        const val COLUMN_NAME_ID = "genre_id"
        const val COLUMN_NAME_NAME = "name"
    }

    object CinemasActorEntity {
        const val TABLE_NAME = "genre"

        const val COLUMN_NAME_CINEMA_ID = "cinema_id"
        const val COLUMN_NAME_ACTOR_NAME = "actor_id"
    }

    object CinemasGenreEntity {
        const val TABLE_NAME = "cinema_genre"

        const val COLUMN_NAME_CINEMA_ID = "cinema_id"
        const val COLUMN_NAME_GENRE_NAME = "genre_id"
    }
}