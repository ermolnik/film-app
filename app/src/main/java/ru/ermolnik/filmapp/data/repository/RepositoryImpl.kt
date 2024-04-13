package ru.ermolnik.filmapp.data.repository

import androidx.paging.PagingData
import ru.ermolnik.filmapp.data.local.db.MoviesLocalDataSource
import ru.ermolnik.filmapp.data.remote.network.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.ermolnik.filmapp.domain.Repository
import ru.ermolnik.filmapp.domain.model.Actor
import ru.ermolnik.filmapp.domain.model.Genre
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.domain.model.MovieDetails
import ru.ermolnik.filmapp.utils.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remote: MoviesRemoteDataSource,
    private val local: MoviesLocalDataSource
) : Repository {

    override suspend fun getPopularMoviesPaging(query: String): Flow<Resource<Flow<PagingData<Movie>>, Throwable>> {
        return flow {
            emit(local.getPopularMoviesPaging(query))
        }
    }

    override suspend fun getPopularMoviesNetwork(): Flow<Resource<List<Movie>, Throwable>> {
        return flow {
            emit(remote.getPopularMoviesNetwork())
        }
    }

    override suspend fun getPopularMoviesLocal(): Flow<Resource<List<Movie>, Throwable>> {
        return flow {
            emit(local.getPopularMoviesLocal())
        }
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        local.insertMovies(movies)
    }

    override suspend fun searchMovies(query: String): Flow<Resource<List<Movie>, Throwable>> {
        return flow {
            emit(remote.searchMovies(query))
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails, Throwable>> {
        return flow {
            emit(remote.getMovieDetails(movieId))
        }
    }

    override suspend fun getMovieActors(movieId: Int): Flow<Resource<List<Actor>, Throwable>> {
        return flow {
            emit(remote.getMovieActors(movieId))
        }
    }

    override suspend fun getGenre(): Flow<Resource<List<Genre>, Throwable>> {
        return flow {
            emit(remote.getGenre())
        }
    }
}