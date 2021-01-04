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
import ru.thstdio.aa2020.data.Cinema
import ru.thstdio.aa2020.data.CinemaDetail

private const val API_KEY =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZjUzODY0YjlmMTViNzE1OWM0YWNlMjE4NWQyNmY2MyIsInN1YiI6IjVlNmExMWE5MzU3YzAwMDAxMzNlZjdiNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4dMlOhOdXjKDjvEHUwAuZDpNBc_xZaEi0z3YMLUqV8w"
private const val BASE_URL = "https://api.themoviedb.org/3/"

@ExperimentalSerializationApi
class Repository() {
    private val api: TimeDbApi = createApi()

    private var configuration: ConfigurationResponse? = null
    private var genres: Map<Long, Genre>? = null

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


    suspend fun downloadMovies(page: Int): Pair<List<Cinema>, Int> = coroutineScope {
        val configurationAndGenres = async { loadConfigurationAndGenres() }
        val responseAsync = async { api.loadNowPlaying(page) }
        val (configuration, genres) = configurationAndGenres.await()
        val response = responseAsync.await()
        response.results.map { item ->
            item.toCinema(
                configuration,
                genres
            )
        } to response.totalPages


    }

    private suspend fun loadConfigurationAndGenres(): Pair<ConfigurationResponse, Map<Long, Genre>> =
        coroutineScope {
            val configurationAsync = async {
                if (configuration == null) {
                    api.loadCnfiguration()
                } else {
                    configuration!!
                }
            }
            val genresAsync = async {
                if (genres == null) {
                    api.loadGenres().genres.associateBy { it.id }
                } else {
                    genres!!
                }
            }
            configurationAsync.await() to genresAsync.await()
        }

    suspend fun downloadMovie(id: Long): CinemaDetail = coroutineScope {
        val configuration: ConfigurationResponse = loadConfigurationAndGenres().first
        val actors = async {
            api.loadDMovieCredits(id).cast.asSequence()
                .filter { castItem -> castItem.profilePath != null }
                .map { it.toActor(configuration) }.toList()
        }
        val cinema = async { api.loadDetailMovie(id) }
        cinema.await().toCinemaDetail(configuration, actors.await())
    }
}


