package ru.ermolnik.filmapp.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ermolnik.filmapp.domain.Repository
import ru.ermolnik.filmapp.domain.model.Actor
import ru.ermolnik.filmapp.utils.Resource
import javax.inject.Inject

class GetMovieActorsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(movieId: Int): Flow<Resource<List<Actor>, Throwable>> {
        return repository.getMovieActors(movieId)
    }
}