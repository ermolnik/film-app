package ru.ermolnik.filmapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.ermolnik.filmapp.domain.model.Actor
import ru.ermolnik.filmapp.domain.use_case.GetMovieActorsUseCase
import ru.ermolnik.filmapp.domain.use_case.GetMovieDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ermolnik.filmapp.utils.doOnError
import ru.ermolnik.filmapp.utils.doOnSuccess

class DetailsViewModel @AssistedInject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieActorsUseCase: GetMovieActorsUseCase,
    @Assisted private val movieId: Int
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Loading)
    val movieDetails: StateFlow<DetailsScreenState> get() = _movieDetails

    init {
        viewModelScope.launch {
            getMovieDetailsUseCase.invoke(movieId).collect {
                it.doOnError { error ->
                    _movieDetails.value = DetailsScreenState.Error(error)
                }
                it.doOnSuccess { content ->
                    var actors: List<Actor> = emptyList()
                    getMovieActorsUseCase.invoke(movieId).collect { credits->
                        credits.doOnSuccess {content->
                            actors = content
                        }
                    }
                    _movieDetails.value = DetailsScreenState.Content(content.copy(actors = actors))
                }
            }
        }
    }
}