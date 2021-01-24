package ru.thstdio.aa2020.cache

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
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
import ru.thstdio.aa2020.data.*
import java.util.concurrent.atomic.AtomicReference


const val ITEMS_SIZE_IN_PAGE = 20

@ExperimentalSerializationApi
class Repository(applicationContext: Context) {
    private val api: TimeDbApi = createApi()
    private val db: AppDb = AppDb.create(applicationContext)

    private val configurationAtomic: AtomicReference<ConfigurationResponse> =
        AtomicReference<ConfigurationResponse>()
    private val genresAtomic: AtomicReference<Map<Long, Genre>> =
        AtomicReference<Map<Long, Genre>>()
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())


    suspend fun getCinemasFromPage(page: Int): CinemaListWithTotalPage =
        coroutineScope {
            val configurationAndGenres = async { getConfigurationAndGenres() }
            val responseAsync = async { api.getNowPlaying(page) }
            val (configuration, genres) = configurationAndGenres.await()
            val response = responseAsync.await()
            saveCinemaListToDbAsync(response.results, page, configuration, genres)
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

    suspend fun getCinemasFromCache(): List<Cinema> =
        db.cinemaDao.getCinemas()
            .map { item -> item.toCinema() }

    suspend fun getCinemaDetail(id: Long): CinemaDetail {
        val configuration: ConfigurationResponse = getConfigurationAndGenres().first
        val actors = api.getMovieCredits(id).cast
            .asSequence()
            .filter { castItem -> castItem.profilePath != null }
            .map { castItem -> castItem.toActor(configuration) }
            .toList()
        val cinemaResponse = api.getDetailMovie(id)
        val cinema = cinemaResponse.toCinemaDetail(configuration, actors)
        saveDetailCinemaAsync(id, cinema, actors)
        return cinema
    }

    suspend fun getCinemasDetailFromCache(id: Long): CinemaDetail =
        db.cinemaDetailDao.getCinemaDetailRelation(id).toCinemaDetail()

    private suspend fun saveCinemaListToDbAsync(
        cinemas: List<CinemaItemResponse>,
        page: Int,
        configuration: ConfigurationResponse,
        genres: Map<Long, Genre>
    ) = scope.async {
        try {
            val listCinemaDto = cinemas.mapIndexed { index, cinemaItemResponse ->
                cinemaItemResponse.toCinemaEntity(
                    configuration = configuration,
                    genres = genres,
                    position = (ITEMS_SIZE_IN_PAGE * (page - 1) + index).toLong()
                )
            }
            db.cinemaDao.insertAll(listCinemaDto)
        } catch (e: Exception) {
        }
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
            val genres = api.getGenresList().genres.map { genreJson -> genreJson.toGenre() }
            val justLoadedGenres: Map<Long, Genre> = genres.associateBy(Genre::id)
            genresAtomic.set(justLoadedGenres)
            saveGenreAsync(genres)
            justLoadedGenres
        }
    }

    private suspend fun saveGenreAsync(genres: List<Genre>) = scope.async {
        try {
            db.cinemaDao.insertGenres(genres.map { genre -> genre.toGenreEntity() })
        } catch (e: Exception) {
        }
    }

    private suspend fun saveDetailCinemaAsync(id: Long, cinema: CinemaDetail, actors: List<Actor>) =
        scope.async {
            try {
                db.cinemaDetailDao.insertCinemaActors(actors
                    .map { actor -> actor.toCinemaActorEntity(id) })
                db.cinemaDetailDao.insertActors(actors.map { actor -> actor.toActorEntity() })
                db.cinemaDetailDao.insert(cinema.toCinemaDetailEntity())
            } catch (e: Exception) {
            }
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

