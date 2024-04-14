package ru.ermolnik.filmapp.data.remote.network

import ru.ermolnik.filmapp.data.remote.dto.toDomain
import ru.ermolnik.filmapp.domain.model.Actor
import ru.ermolnik.filmapp.domain.model.Genre
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.domain.model.MovieDetails
import ru.ermolnik.filmapp.utils.Result
import ru.ermolnik.filmapp.utils.runOperationCatching
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getPopularMoviesNetwork(): Result<List<Movie>, Throwable> {
        return runOperationCatching {
            apiInterface.getPopularMovies(page = 1).toDomain()
        }
    }

    suspend fun searchMovies(query: String): Result<List<Movie>, Throwable> {
        return runOperationCatching {
            apiInterface.searchMovies(query = query, page = 1).toDomain()
        }
    }

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails, Throwable> {
        return runOperationCatching {
            apiInterface.getMovieDetails(movieId).toDomain()
        }
    }

    suspend fun getMovieActors(movieId: Int): Result<List<Actor>, Throwable> {
        return runOperationCatching { apiInterface.getMovieActors(movieId).cast.toDomain() }
    }

    suspend fun getGenre(): Result<List<Genre>, Throwable> {
        return runOperationCatching { apiInterface.getGenre().toDomain() }
    }
}