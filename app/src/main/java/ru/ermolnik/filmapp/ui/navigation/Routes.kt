package ru.ermolnik.filmapp.ui.navigation


sealed class Routes(val routes: String) {
    data object MainScreen : Routes("MainScreen")
    data object DetailsScreen : Routes("DetailsScreen")
}
