package ru.ermolnik.filmapp.data.local.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import ru.ermolnik.filmapp.data.local.dao.MoviesDao
import ru.ermolnik.filmapp.data.MoviesRemoteMediator
import ru.ermolnik.filmapp.data.remote.network.ApiInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ermolnik.filmapp.data.local.dao.RemoteKeysDao
import ru.ermolnik.filmapp.data.local.entity.toData
import ru.ermolnik.filmapp.data.local.entity.toDomain
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.utils.Result
import ru.ermolnik.filmapp.utils.runOperationCatching
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val apiInterface: ApiInterface,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularMoviesPaging(query: String): Result<Flow<PagingData<Movie>>, Throwable> {
        val pagingSourceFactory = { moviesDao.getMoviesPaging() }
        val paging = Pager(
            config = PagingConfig(
                pageSize = 3,
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = MoviesRemoteMediator(
                query = query,
                dbDao = moviesDao,
                remoteKeysDao = remoteKeysDao,
                apiService = apiInterface
            ),
        ).flow.map { pagingData ->
            pagingData.map { movieEntity ->
                movieEntity.toDomain()
            }
        }
        return runOperationCatching { paging }
    }

    suspend fun getPopularMoviesLocal(): Result<List<Movie>, Throwable> {
        return runOperationCatching {
            moviesDao.getMovies().map { it.toDomain() }
        }
    }

    suspend fun insertMovies(movies: List<Movie>) {
        moviesDao.insertMovies(movies.map { it.toData() })
    }
}