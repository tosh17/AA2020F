package ru.thstdio.aa2020.cache

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.thstdio.aa2020.api.interceptor.ApiHeaderInterceptor
import ru.thstdio.aa2020.api.response.*
import ru.thstdio.aa2020.api.service.TimeDbApi
import ru.thstdio.aa2020.cache.entity.*
import ru.thstdio.aa2020.cache.relation.toCinema
import ru.thstdio.aa2020.cache.relation.toCinemaDetail
import ru.thstdio.aa2020.data.Cinema
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.data.CinemaListWithTotalPage
import ru.thstdio.aa2020.data.Genre
import java.util.concurrent.atomic.AtomicReference


@ExperimentalSerializationApi
class Repository(applicationContext: Context) {
    private val api: TimeDbApi = createApi()
    private val db: AppDb = AppDb.create(applicationContext)

    private var configurationAtomic: AtomicReference<ConfigurationResponse> =
        AtomicReference<ConfigurationResponse>()
    private var genresAtomic: AtomicReference<Map<Long, Genre>> =
        AtomicReference<Map<Long, Genre>>()


    suspend fun getMoviesFromPage(page: Int): CinemaListWithTotalPage =
        coroutineScope {
            val configurationAndGenres = async { getConfigurationAndGenres() }
            val responseAsync = async { api.getNowPlaying(page) }
            val (configuration, genres) = configurationAndGenres.await()
            val response = responseAsync.await()
            async {
                val listCinemaDto = response.results.mapIndexed { index, cinemaItemResponse ->
                    cinemaItemResponse.toCinemaEntity(
                        configuration,
                        genres,
                        20L * (page - 1) + index
                    )
                }
                db.cinemaDao.insertAll(listCinemaDto)
            }

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


    suspend fun getMoviesFromCache(): List<Cinema> =
        coroutineScope {
            db.cinemaDao.getCinemaAll().sortedBy { it.cinema.position }.map { it.toCinema() }
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

    private suspend fun getGenres(): Map<Long, Genre> = coroutineScope {
        val alreadyLoadedGenres = genresAtomic.get()
        if (alreadyLoadedGenres != null) {
            alreadyLoadedGenres
        } else {
            val genres = api.getGenresList().genres.map { genreJson -> genreJson.toGenre() }
            val justLoadedGenres: Map<Long, Genre> = genres.associateBy { it.id }
            genresAtomic.set(justLoadedGenres)
            async {
                db.cinemaDao.insertGenresAll(genres.map { genre -> genre.toGenreEntity() })
            }
            justLoadedGenres
        }
    }

    suspend fun getMoviesDetail(id: Long): CinemaDetail = coroutineScope {
        val configuration: ConfigurationResponse = getConfigurationAndGenres().first
        val actors = async {
            val actors = api.getMovieCredits(id).cast
                .asSequence()
                .filter { castItem -> castItem.profilePath != null }
                .map { it.toActor(configuration) }
                .toList()

            db.cinemaDetailDto.insertCinemaActorAll(actors.map { it.toCinemaActorEntity(id) })
            db.cinemaDetailDto.insertActorAll(actors.map { it.toActorEntity() })
            actors
        }
        val cinemaResponse = async { api.getDetailMovie(id) }

        val cinema = cinemaResponse.await().toCinemaDetail(configuration, actors.await())
        async { db.cinemaDetailDto.insert(cinema.toCinemaDetailEntity()) }
        cinema
    }

    suspend fun getMoviesDetailFromCache(id: Long): CinemaDetail = coroutineScope {
        db.cinemaDetailDto.getMovieDetail(id).toCinemaDetail()
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

