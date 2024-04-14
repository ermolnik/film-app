package ru.ermolnik.filmapp.ui.details

import ru.ermolnik.filmapp.domain.model.MovieDetails

internal sealed class DetailsScreenState {
    data object Loading : DetailsScreenState()
    data class Error(val error: Throwable) : DetailsScreenState()
    data class Content(val data: MovieDetails) : DetailsScreenState()
}