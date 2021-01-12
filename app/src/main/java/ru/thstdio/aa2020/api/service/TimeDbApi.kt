package ru.thstdio.aa2020.api.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.thstdio.aa2020.api.response.*

interface TimeDbApi {
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    @GET("genre/movie/list")
    suspend fun getGenresList(): GenresResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int,
        @Query("language") language: String = "ru"
    ): NowPlayingResponse


    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "ru"
    ): MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "ru"
    ): MovieCreditsResponse
}