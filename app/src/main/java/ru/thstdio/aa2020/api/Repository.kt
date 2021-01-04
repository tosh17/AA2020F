package ru.thstdio.aa2020.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
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
import ru.thstdio.aa2020.api.response.ConfigResponse
import ru.thstdio.aa2020.api.response.Genre
import ru.thstdio.aa2020.api.service.TimeDbApi
import ru.thstdio.aa2020.data.Cinema
import ru.thstdio.aa2020.data.CinemaDetail

private const val Api_Key =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZjUzODY0YjlmMTViNzE1OWM0YWNlMjE4NWQyNmY2MyIsInN1YiI6IjVlNmExMWE5MzU3YzAwMDAxMzNlZjdiNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4dMlOhOdXjKDjvEHUwAuZDpNBc_xZaEi0z3YMLUqV8w"
private const val Base_Url = "https://api.themoviedb.org/3/"

@ExperimentalSerializationApi
class Repository() {
    private val api: TimeDbApi

    init {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .addInterceptor(ApiHeaderInterceptor(Api_Key))
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Base_Url)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        api = retrofit.create(TimeDbApi::class.java)
    }

    var config: ConfigResponse? = null
    var genres: Map<Long, Genre>? = null

    suspend fun downloadMovies(page: Int): Pair<List<Cinema>, Int> {
        if (config == null) config = api.loadConfig()
        if (genres == null) genres = api.loadGenres().genres.associateBy { it.id }
        val response = api.loadNowPlaying(page)
        return response.results.map { item ->
            item.toCinema(
                config!!,
                genres!!
            )
        } to response.totalPages
    }

    suspend fun downloadMovie(id: Long): CinemaDetail {
        val scope = CoroutineScope(SupervisorJob())
        val job = scope.async {
            val actors = async {
                val credits = api.loadDMovieCredits(id)
                credits.cast.asSequence().filter { it.profilePath != null }
                    .map { it.toActor(config!!) }.toList()
            }
            val cinema = async { api.loadDetailMovie(id) }
            actors.await() to cinema.await()
        }
        val (actors, cinema) = job.await()
        return cinema.toCinemaDetail(config!!, actors)
    }


}