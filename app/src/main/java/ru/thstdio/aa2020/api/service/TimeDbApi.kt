package ru.thstdio.aa2020.api.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.thstdio.aa2020.api.response.*

interface TimeDbApi {
    @GET("configuration")
    suspend fun loadCnfiguration(): ConfigurationResponse

    @GET("genre/movie/list")
    suspend fun loadGenres(): GenresResponse

    @GET("movie/now_playing")
    suspend fun loadNowPlaying(
        @Query("page") page: Int,
        @Query("language") language: String = "ru"
    ): NowPlayingResponse


    @GET("movie/{movie_id}")
    suspend fun loadDetailMovie(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "ru"
    ): MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun loadDMovieCredits(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "ru"
    ): MovieCreditsResponse
}