package ru.ermolnik.filmapp.di

import dagger.Module
import dagger.Provides
import ru.ermolnik.filmapp.domain.use_case.FilterByGenresUseCase
import ru.ermolnik.filmapp.domain.use_case.GetGenreUseCase
import ru.ermolnik.filmapp.domain.use_case.GetPopularMoviesPaging
import ru.ermolnik.filmapp.ui.list.MainViewModelFactory

@Module
class ViewModelModule {

    @Provides
    fun provideMainScreenViewModelFactory(
        getGenreUseCase: GetGenreUseCase,
        filterByGenresUseCase: FilterByGenresUseCase,
        getPopularMoviesPaging: GetPopularMoviesPaging,
    ): MainViewModelFactory {
        return MainViewModelFactory(
            getGenreUseCase,
            filterByGenresUseCase,
            getPopularMoviesPaging
        )
    }
}