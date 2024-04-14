package ru.ermolnik.filmapp.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ermolnik.filmapp.domain.Repository
import ru.ermolnik.filmapp.domain.model.MovieDetails
import ru.ermolnik.filmapp.utils.Result
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(movieId: Int): Flow<Result<MovieDetails, Throwable>> {
        return repository.getMovieDetails(movieId)
    }
}