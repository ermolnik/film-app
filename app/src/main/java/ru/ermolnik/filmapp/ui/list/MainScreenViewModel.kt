package ru.ermolnik.filmapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import ru.ermolnik.filmapp.domain.model.Genre
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.domain.use_case.FilterByGenresUseCase
import ru.ermolnik.filmapp.domain.use_case.GetGenreUseCase
import ru.ermolnik.filmapp.domain.use_case.GetPopularMoviesPaging
import ru.ermolnik.filmapp.utils.doOnError
import ru.ermolnik.filmapp.utils.doOnSuccess
import javax.inject.Inject

internal class MainScreenViewModel @Inject constructor(
    private val getGenreUseCase: GetGenreUseCase,
    private val filterByGenresUseCase: FilterByGenresUseCase,
    private val getPopularMoviesPaging: GetPopularMoviesPaging,
) : ViewModel() {

    private val _movies = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val movie = _movies.asStateFlow()

    private lateinit var movieList: Flow<PagingData<Movie>>

    private val _genre = MutableStateFlow(emptyList<Genre>())
    val genre = _genre.asStateFlow()

    init {
        viewModelScope.launch {
            getGenreUseCase.invoke().collect {
                it.doOnSuccess { genres ->
                    _genre.value = genres
                }
                it.doOnError {
                    _genre.value = emptyList()
                }
            }
            getMovies()
        }
    }

    fun onValueChange(newText: String) {
        viewModelScope.launch {
            if (newText.isNotBlank()) {
                getMovies(newText)
            }
        }
    }

    fun leadingIconClicked() {
        viewModelScope.launch {
            getGenreUseCase.invoke().collect {
                it.doOnSuccess { genres ->
                    _genre.value = genres
                }
                it.doOnError {
                    _genre.value = emptyList()
                }
            }
        }
        getMovies()
    }

    fun getMovies(query: String = "") {
        viewModelScope.launch {
            _movies.emit(MainScreenState.Loading)
            getPopularMoviesPaging.invoke(query).collect {
                it.doOnSuccess { resourceData ->
                    _movies.emit(MainScreenState.Content(resourceData))
                    movieList = resourceData
                }
            }
        }
    }
}