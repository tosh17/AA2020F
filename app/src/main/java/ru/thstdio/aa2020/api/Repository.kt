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
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    suspend fun downloadMovies(): List<Movie> {
        val moviesJson = loadFromJson<List<JsonMovie>>("data.json")
        { jsonString: String ->
            jsonFormat.decodeFromString(jsonString)
        }
        val actors = loadActors().associateBy { it.id }
        val genres = loadGenres().associateBy { it.id }
        return moviesJson.map { it.toMovie(actors, genres) }
    }

    suspend fun downloadMovie(id: Long): Movie =
        withContext(Dispatchers.IO) {
            delay(1400)
            downloadMovies().first { it.id == id }
        }


    private suspend fun loadGenres(): List<Genre> =
        loadFromJson<List<JsonGenre>>(
            "genres.json"
        ) { jsonString: String ->
            jsonFormat.decodeFromString(jsonString)
        }
            .map { it.toGenre() }

    private suspend fun loadActors(): List<Actor> =
        loadFromJson<List<JsonActor>>("people.json") { jsonString: String ->
            jsonFormat.decodeFromString(jsonString)
        }.map { it.toActor() }


    private suspend fun <T> loadFromJson(fileName: String, parse: Json.(String) -> T): T =
        withContext(Dispatchers.IO) {
            val jsonString = readAssetFileToString(appContext, fileName)
            jsonFormat.parse(jsonString)
        }

    private fun readAssetFileToString(context: Context, fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }
}