package ru.ermolnik.filmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import ru.ermolnik.filmapp.navigation.Navigation
import ru.ermolnik.filmapp.ui.details.DetailsViewModelAssistedFactory
import ru.ermolnik.filmapp.ui.list.MainViewModelFactory
import ru.ermolnik.filmapp.ui.theme.FilmAppTheme
import javax.inject.Inject

internal class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    @Inject
    lateinit var detailsViewModelAssistedFactory: DetailsViewModelAssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FilmAppTheme {
                (applicationContext as App).appComponent.inject(this)
                val navController = rememberNavController()
                Navigation(
                    navController, mainViewModelFactory, detailsViewModelAssistedFactory
                )
            }
        }
    }
}