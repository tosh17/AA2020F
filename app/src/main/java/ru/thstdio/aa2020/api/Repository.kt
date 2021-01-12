package ru.thstdio.aa2020.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.thstdio.aa2020.api.converter.toActor
import ru.thstdio.aa2020.api.converter.toCinema
import ru.thstdio.aa2020.api.converter.toCinemaDetail
import ru.thstdio.aa2020.api.interceptor.ApiHeaderInterceptor
import ru.thstdio.aa2020.api.response.ConfigurationResponse
import ru.thstdio.aa2020.api.response.Genre
import ru.thstdio.aa2020.api.service.TimeDbApi
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.data.CinemaListWithTotalPage
import java.util.concurrent.atomic.AtomicReference


@ExperimentalSerializationApi
class Repository() {
    private val api: TimeDbApi = createApi()

    private var configurationAtomic: AtomicReference<ConfigurationResponse> =
        AtomicReference<ConfigurationResponse>()
    private var genresAtomic: AtomicReference<Map<Long, Genre>> =
        AtomicReference<Map<Long, Genre>>()

    suspend fun getMoviesFromPage(page: Int): CinemaListWithTotalPage = coroutineScope {
        val configurationAndGenres = async { getConfigurationAndGenres() }
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

    private suspend fun getConfigurationAndGenres(): Pair<ConfigurationResponse, Map<Long, Genre>> =
        coroutineScope {
            val configurationAsync = async { getConfiguration() }
            val genresAsync = async { getGenres() }
            configurationAsync.await() to genresAsync.await()
        }

    private suspend fun getConfiguration(): ConfigurationResponse {
        val alreadyLoadedConfiguration = configurationAtomic.get()
        return if (alreadyLoadedConfiguration != null) {
            alreadyLoadedConfiguration
        } else {
            val justLoadedConfiguration = api.getConfiguration()
            configurationAtomic.set(justLoadedConfiguration)
            justLoadedConfiguration
        }

    }

    private suspend fun getGenres(): Map<Long, Genre> {
        val alreadyLoadedGenres = genresAtomic.get()
        return if (alreadyLoadedGenres != null) {
            alreadyLoadedGenres
        } else {
            val justLoadedGenres = api.getGenresList().genres.associateBy { it.id }
            genresAtomic.set(justLoadedGenres)
            justLoadedGenres
        }
    }

    suspend fun getMoviesDetail(id: Long): CinemaDetail = coroutineScope {
        val configuration: ConfigurationResponse = getConfigurationAndGenres().first
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

private const val API_KEY =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZjUzODY0YjlmMTViNzE1OWM0YWNlMjE4NWQyNmY2MyIsInN1YiI6IjVlNmExMWE5MzU3YzAwMDAxMzNlZjdiNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4dMlOhOdXjKDjvEHUwAuZDpNBc_xZaEi0z3YMLUqV8w"
private const val BASE_URL = "https://api.themoviedb.org/3/"

private fun createApi(): TimeDbApi {
    val json = Json {
        ignoreUnknownKeys = true
    }
    val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .addInterceptor(ApiHeaderInterceptor(API_KEY))
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    return retrofit.create(TimeDbApi::class.java)
}

