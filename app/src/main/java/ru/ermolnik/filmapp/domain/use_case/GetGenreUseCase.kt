package ru.ermolnik.filmapp.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ermolnik.filmapp.domain.Repository
import ru.ermolnik.filmapp.domain.model.Genre
import ru.ermolnik.filmapp.utils.Result
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): Flow<Result<List<Genre>, Throwable>> {
        return repository.getGenre()
    }
}