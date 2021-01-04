package ru.thstdio.aa2020.api

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import ru.thstdio.aa2020.api.converter.toActor
import ru.thstdio.aa2020.api.converter.toCinema
import ru.thstdio.aa2020.api.converter.toCinemaDetail
import ru.thstdio.aa2020.api.response.ConfigurationResponse
import ru.thstdio.aa2020.api.response.Genre
import ru.thstdio.aa2020.api.service.Service
import ru.thstdio.aa2020.api.service.TimeDbApi
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.data.CinemaListWithTotalPage


@ExperimentalSerializationApi
class Repository() {
    private val api: TimeDbApi = Service().createApi()

    private var configuration: ConfigurationResponse? = null
    private var genres: Map<Long, Genre>? = null


    suspend fun downloadMovies(page: Int): CinemaListWithTotalPage = coroutineScope {
        val configurationAndGenres = async { loadConfigurationAndGenres() }
        val responseAsync = async { api.getNowPlaying(page) }
        val (configuration, genres) = configurationAndGenres.await()
        val response = responseAsync.await()
        CinemaListWithTotalPage(
            list = response.results.map { item ->
                item.toCinema(
                    configuration,
                    genres
                )
            },
            totalPage = response.totalPages
        )
    }

    private suspend fun loadConfigurationAndGenres(): Pair<ConfigurationResponse, Map<Long, Genre>> =
        coroutineScope {
            val configurationAsync = async {
                configuration = configuration ?: api.getConfiguration()
                configuration!!
            }
            val genresAsync = async {
                genres = genres ?: api.getGenresList().genres.associateBy { it.id }
                genres!!
            }
            configurationAsync.await() to genresAsync.await()
        }

    suspend fun downloadMovie(id: Long): CinemaDetail = coroutineScope {
        val configuration: ConfigurationResponse = loadConfigurationAndGenres().first
        val actors = async {
            api.getMovieCredits(id).cast
                .asSequence()
                .filter { castItem -> castItem.profilePath != null }
                .map { it.toActor(configuration) }
                .toList()
        }
        val cinema = async { api.getDetailMovie(id) }
        cinema.await().toCinemaDetail(configuration, actors.await())
    }
}


