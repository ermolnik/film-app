package ru.ermolnik.filmapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ermolnik.filmapp.ui.details.DetailsScreen
import ru.ermolnik.filmapp.ui.details.DetailsViewModel
import ru.ermolnik.filmapp.ui.details.DetailsViewModelAssistedFactory
import ru.ermolnik.filmapp.ui.details.DetailsViewModelFactory
import ru.ermolnik.filmapp.ui.list.MainScreen
import ru.ermolnik.filmapp.ui.list.MainScreenViewModel
import ru.ermolnik.filmapp.ui.list.MainViewModelFactory

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModelFactory: MainViewModelFactory,
    detailsViewModelAssistedFactory: DetailsViewModelAssistedFactory
) {
    NavHost(
        navController = navController, startDestination = Routes.MainScreen.routes
    ) {
        composable(Routes.MainScreen.routes) {
            val mainScreenViewModel: MainScreenViewModel = viewModel(
                factory = mainViewModelFactory,
            )
            MainScreen(navController = navController, mainScreenViewModel)
        }
        composable("${Routes.DetailsScreen.routes}/{filmId}") {
            it.arguments?.let { arguments ->
                val detailsViewModel: DetailsViewModel = viewModel(
                    factory = DetailsViewModelFactory(
                        detailsViewModelAssistedFactory, arguments.getString("filmId")!!.toInt()
                    )
                )
                DetailsScreen(
                    detailsViewModel,
                )
            }
        }
    }
}