package ru.thstdio.aa2020.api.util

import android.content.Context
import ru.thstdio.aa2020.api.loadMovie
import ru.thstdio.aa2020.api.loadMovies

class Repository(private val context: Context) {

    suspend fun downloadMovies() = loadMovies(context)
    suspend fun downloadMovie(id: Long) = loadMovie(context, id)
}