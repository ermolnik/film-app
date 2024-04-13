package ru.ermolnik.filmapp.di

import dagger.Module
import dagger.Provides
import ru.ermolnik.filmapp.domain.Repository
import ru.ermolnik.filmapp.domain.use_case.GetGenreUseCase
import ru.ermolnik.filmapp.domain.use_case.GetMovieActorsUseCase
import ru.ermolnik.filmapp.domain.use_case.GetMovieDetailsUseCase

@Module
class DomainModule {

    @Provides
    fun provideGetMovieActorsUseCase(repository: Repository): GetMovieActorsUseCase {
        return GetMovieActorsUseCase(repository)
    }

    @Provides
    fun provideGetMovieDetailsUseCase(repository: Repository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(repository)
    }

    @Provides
    fun provideGetGenreUseCase(repository: Repository): GetGenreUseCase {
        return GetGenreUseCase(repository)
    }

}