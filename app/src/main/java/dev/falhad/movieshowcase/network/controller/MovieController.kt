package dev.falhad.movieshowcase.network.controller

import dev.falhad.movieshowcase.network.model.MovieResponse
import dev.falhad.movieshowcase.network.model.SearchResponse
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface MovieController {


    @GET("/")
    suspend fun byIMDBId(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String,
    ): Response<MovieResponse>

    @GET("/")
    suspend fun search(
        @Query("apikey") apiKey: String,
        @Query("s") term: String,
        @Query("y") year: Int?,
        @Query("type") type: MovieType?,
    ): Response<SearchResponse>

}

enum class MovieType{
    movie, series, episode
}