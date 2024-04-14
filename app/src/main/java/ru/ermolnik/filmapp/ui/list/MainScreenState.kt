package ru.ermolnik.filmapp.ui.list

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.ermolnik.filmapp.domain.model.Movie

internal sealed class MainScreenState {
    data object Loading : MainScreenState()
    data class Error(val error: Throwable) : MainScreenState()
    data class Content(val data: Flow<PagingData<Movie>>) : MainScreenState()
}
