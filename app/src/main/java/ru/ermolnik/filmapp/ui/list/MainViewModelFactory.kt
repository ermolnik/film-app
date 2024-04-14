package ru.ermolnik.filmapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermolnik.filmapp.domain.use_case.FilterByGenresUseCase
import ru.ermolnik.filmapp.domain.use_case.GetGenreUseCase
import ru.ermolnik.filmapp.domain.use_case.GetPopularMoviesPaging
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val getGenreUseCase: GetGenreUseCase,
    private val filterByGenresUseCase: FilterByGenresUseCase,
    private val getPopularMoviesPaging: GetPopularMoviesPaging,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(
            getGenreUseCase,
            filterByGenresUseCase,
            getPopularMoviesPaging
        ) as T
    }
}