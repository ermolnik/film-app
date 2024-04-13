package ru.ermolnik.filmapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.ermolnik.filmapp.domain.model.Actor
import ru.ermolnik.filmapp.domain.model.Genre
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.domain.model.MovieDetails
import ru.ermolnik.filmapp.utils.Resource

interface Repository {
    suspend fun getPopularMoviesNetwork(): Flow<Resource<List<Movie>, Throwable>>
    suspend fun getPopularMoviesLocal(): Flow<Resource<List<Movie>, Throwable>>
    suspend fun getPopularMoviesPaging(query: String): Flow<Resource<Flow<PagingData<Movie>>, Throwable>>
    suspend fun insertMovies(movies: List<Movie>)
    suspend fun searchMovies(query: String): Flow<Resource<List<Movie>, Throwable>>
    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails, Throwable>>
    suspend fun getMovieActors(movieId: Int): Flow<Resource<List<Actor>, Throwable>>
    suspend fun getGenre(): Flow<Resource<List<Genre>, Throwable>>
}