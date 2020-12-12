package ru.thstdio.aa2020.api

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.thstdio.aa2020.api.response.toActor
import ru.thstdio.aa2020.api.response.toGenre
import ru.thstdio.aa2020.api.response.toMovie
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.Genre
import ru.thstdio.aa2020.data.Movie


suspend fun loadGenres(context: Context): List<Genre> =
    loadGenresFromJson(context).map { it.toGenre() }

suspend fun loadActors(context: Context): List<Actor> =
    loadActorsFromJson(context).map { it.toActor() }

suspend fun loadMovies(context: Context): List<Movie> {
    val moviesJson = loadMoviesFromJson(context)
    val actors = loadActors(context).associateBy { it.id }
    val genres = loadGenres(context).associateBy { it.id }
    return moviesJson.map { it.toMovie(actors, genres) }
}

suspend fun loadMovie(context: Context, movieId: Long): Movie =
    withContext(Dispatchers.IO) {
        delay(1400)
        Log.d("TEST", "load  $movieId")
        loadMovies(context).first { it.id == movieId }
    }
