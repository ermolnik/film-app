package ru.ermolnik.filmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import ru.ermolnik.filmapp.ui.details.DetailsViewModelAssistedFactory
import ru.ermolnik.filmapp.ui.list.MainViewModelFactory
import ru.ermolnik.filmapp.ui.navigation.Navigation
import ru.ermolnik.filmapp.ui.theme.FilmAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

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