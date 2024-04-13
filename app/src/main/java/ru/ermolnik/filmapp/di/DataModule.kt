package ru.ermolnik.filmapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ermolnik.filmapp.data.local.MoviesDb
import ru.ermolnik.filmapp.data.local.dao.MoviesDao
import ru.ermolnik.filmapp.data.local.dao.RemoteKeysDao
import ru.ermolnik.filmapp.data.local.db.MoviesLocalDataSource
import ru.ermolnik.filmapp.data.remote.network.ApiInterface
import ru.ermolnik.filmapp.data.remote.network.MoviesRemoteDataSource
import ru.ermolnik.filmapp.data.remote.network.RetrofitClient
import ru.ermolnik.filmapp.data.repository.RepositoryImpl
import ru.ermolnik.filmapp.domain.Repository

@Module
class DataModule {

    @Provides
    fun provideDb(context: Context): MoviesDb {
        return MoviesDb.getDatabase(context)
    }

    @Provides
    fun provideRemoteKeysDao(moviesDb: MoviesDb): RemoteKeysDao = moviesDb.remoteKeysDao()

    @Provides
    fun provideRemoteMoviesDao(moviesDb: MoviesDb): MoviesDao = moviesDb.moviesDao()

    @Provides
    fun provideApi(): ApiInterface {
        return RetrofitClient.create()
    }

    @Provides
    fun provideMoviesLocalDataSource(
        moviesDao: MoviesDao,
        remoteKeysDao: RemoteKeysDao,
        apiInterface: ApiInterface
    ): MoviesLocalDataSource {
        return MoviesLocalDataSource(moviesDao,remoteKeysDao, apiInterface)
    }

    @Provides
    fun provideMoviesRemoteDataSource(apiInterface: ApiInterface): MoviesRemoteDataSource {
        return MoviesRemoteDataSource(apiInterface)
    }

    @Provides
    fun provideRepository(
        moviesLocalDataSource: MoviesLocalDataSource,
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): Repository {
        return RepositoryImpl(moviesRemoteDataSource, moviesLocalDataSource)
    }

}
