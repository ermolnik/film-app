package ru.ermolnik.filmapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory

class DetailsViewModelFactory(
    private val detailsViewModelAssistedFactory: DetailsViewModelAssistedFactory,
    private val movieId: Int,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return detailsViewModelAssistedFactory.create(movieId) as T
    }
}

@AssistedFactory
interface DetailsViewModelAssistedFactory {
    fun create(movieId: Int): DetailsViewModel
}