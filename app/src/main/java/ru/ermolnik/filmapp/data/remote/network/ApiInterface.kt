package ru.ermolnik.filmapp.data.remote.network

import ru.ermolnik.filmapp.data.remote.dto.ActorsDto
import ru.ermolnik.filmapp.data.remote.dto.GenresDto
import ru.ermolnik.filmapp.data.remote.dto.MovieDetailsDto
import ru.ermolnik.filmapp.data.remote.dto.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MoviesDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): MoviesDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): MovieDetailsDto

    @GET("movie/{movie_id}/credits?")
    suspend fun getMovieActors(
        @Path("movie_id") movieId: Int,
    ): ActorsDto

    @GET("genre/movie/list")
    suspend fun getGenre(): GenresDto
}