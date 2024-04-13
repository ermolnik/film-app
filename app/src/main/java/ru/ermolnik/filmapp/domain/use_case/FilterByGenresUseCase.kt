package ru.ermolnik.filmapp.domain.use_case

import ru.ermolnik.filmapp.domain.model.Movie
import javax.inject.Inject

class FilterByGenresUseCase @Inject constructor() {
    operator fun invoke(movies: List<Movie>, genreId: Int): List<Movie> {
        return movies.filter { it.genres.contains(genreId) }
    }
}