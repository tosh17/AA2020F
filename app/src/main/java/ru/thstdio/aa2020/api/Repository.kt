package ru.thstdio.aa2020.api

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.thstdio.aa2020.api.response.*
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.Genre
import ru.thstdio.aa2020.data.Movie

class Repository(private val appContext: Context) {

    suspend fun downloadMovies() = loadMovies(appContext)
    suspend fun downloadMovie(id: Long) = loadMovie(appContext, id)

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    private suspend fun loadGenres(context: Context): List<Genre> =
        loadFromJson<JsonGenre>("genres.json").map { it.toGenre() }

    private suspend fun loadActors(context: Context): List<Actor> =
        loadFromJson<JsonActor>("people.json").map { it.toActor() }

    private suspend fun loadMovies(context: Context): List<Movie> {
        val moviesJson = loadFromJson<JsonMovie>("data.json")
        val actors = loadActors(context).associateBy { it.id }
        val genres = loadGenres(context).associateBy { it.id }
        return moviesJson.map { it.toMovie(actors, genres) }
    }

    private suspend fun loadMovie(context: Context, movieId: Long): Movie =
        withContext(Dispatchers.IO) {
            delay(1400)
            loadMovies(context).first { it.id == movieId }
        }


    private suspend inline fun <reified T> loadFromJson(fileName: String): List<T> =
        withContext(Dispatchers.IO) {
            val jsonString = readAssetFileToString(appContext, fileName)
            jsonFormat.decodeFromString<List<T>>(jsonString)
        }

    private fun readAssetFileToString(context: Context, fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }
}