package ru.ermolnik.filmapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.ermolnik.filmapp.domain.model.Actor
import ru.ermolnik.filmapp.domain.model.Genre
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.domain.model.MovieDetails
import ru.ermolnik.filmapp.utils.Result

interface Repository {
    suspend fun getPopularMoviesNetwork(): Flow<Result<List<Movie>, Throwable>>
    suspend fun getPopularMoviesLocal(): Flow<Result<List<Movie>, Throwable>>
    suspend fun getPopularMoviesPaging(query: String): Flow<Result<Flow<PagingData<Movie>>, Throwable>>
    suspend fun insertMovies(movies: List<Movie>)
    suspend fun searchMovies(query: String): Flow<Result<List<Movie>, Throwable>>
    suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails, Throwable>>
    suspend fun getMovieActors(movieId: Int): Flow<Result<List<Actor>, Throwable>>
    suspend fun getGenre(): Flow<Result<List<Genre>, Throwable>>
}