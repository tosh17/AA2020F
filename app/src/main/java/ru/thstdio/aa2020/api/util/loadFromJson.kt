package ru.thstdio.aa2020.api


import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.thstdio.aa2020.api.response.JsonActor
import ru.thstdio.aa2020.api.response.JsonGenre
import ru.thstdio.aa2020.api.response.JsonMovie


private val jsonFormat = Json { ignoreUnknownKeys = true }


suspend fun loadGenresFromJson(context: Context): List<JsonGenre> =
    withContext(Dispatchers.IO) {
        val jsonString = readAssetFileToString(context, "genres.json")
        jsonFormat.decodeFromString<List<JsonGenre>>(jsonString)
    }

suspend fun loadActorsFromJson(context: Context): List<JsonActor> =
    withContext(Dispatchers.IO) {
        val jsonString = readAssetFileToString(context, "people.json")
        jsonFormat.decodeFromString<List<JsonActor>>(jsonString)
    }


suspend fun loadMoviesFromJson(context: Context): List<JsonMovie> =
    withContext(Dispatchers.IO) {
        val jsonString = readAssetFileToString(context, "data.json")
        jsonFormat.decodeFromString<List<JsonMovie>>(jsonString)
    }


private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}








