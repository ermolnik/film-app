package ru.ermolnik.filmapp.domain.use_case

import androidx.paging.PagingData
import androidx.paging.filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.ermolnik.filmapp.domain.model.Movie
import javax.inject.Inject

class FilterByGenresUseCase @Inject constructor() {
    operator fun invoke(movies: PagingData<Movie>, genreId: Int): Flow<PagingData<Movie>> {
        return flow {
            movies.filter {
                it.genres.contains(genreId)
            }
        }
    }
}