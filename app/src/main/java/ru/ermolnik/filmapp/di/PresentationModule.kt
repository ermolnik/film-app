package ru.ermolnik.filmapp.di

import dagger.Module
import dagger.Provides
import ru.ermolnik.filmapp.domain.use_case.GetGenreUseCase
import ru.ermolnik.filmapp.domain.use_case.GetPopularMoviesPaging
import ru.ermolnik.filmapp.ui.list.MainViewModelFactory

@Module
class PresentationModule {

    @Provides
    fun provideMainScreenViewModelFactory(
        getGenreUseCase: GetGenreUseCase,
        getPopularMoviesPaging: GetPopularMoviesPaging,
    ): MainViewModelFactory {
        return MainViewModelFactory(
            getGenreUseCase,
            getPopularMoviesPaging
        )
    }
}