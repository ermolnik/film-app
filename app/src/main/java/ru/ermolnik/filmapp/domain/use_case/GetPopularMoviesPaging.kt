package ru.ermolnik.filmapp.domain.use_case

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.ermolnik.filmapp.domain.Repository
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.utils.Result
import javax.inject.Inject

class GetPopularMoviesPaging @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(query: String): Flow<Result<Flow<PagingData<Movie>>, Throwable>> {
        return repository.getPopularMoviesPaging(query)
    }
}