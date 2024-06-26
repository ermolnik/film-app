package ru.ermolnik.filmapp.ui.list

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.coroutines.delay
import ru.ermolnik.filmapp.shared_ui.FilmCard
import ru.ermolnik.filmapp.shared_ui.SearchBar
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.shared_ui.CategoriesItem
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
internal fun MainScreen(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel
) {
    val state = mainScreenViewModel.movie.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    val category = remember { mutableStateOf("Popular now") }
    val templateSearchValue = remember { mutableStateOf("") }

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            pullToRefreshState.startRefresh()
            mainScreenViewModel.getMovies()
            delay(1000)
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier = Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            SearchBar(onValueChange = {
                templateSearchValue.value = it
                mainScreenViewModel.onValueChange(templateSearchValue.value)
            }, leadingIconClicked = {
                templateSearchValue.value = ""
                category.value = "Popular now"
                mainScreenViewModel.leadingIconClicked()
            })

            Text(
                text = category.value,
                modifier = Modifier.padding(start = 20.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            if (false) {
                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = 20.dp, vertical = 18.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(mainScreenViewModel.genre.value) {
                        CategoriesItem(category = it, onclick = { genre ->
                            category.value = genre.name
                        })
                    }
                }
            }

            val scaleFraction = if (pullToRefreshState.isRefreshing) 1f else
                LinearOutSlowInEasing.transform(pullToRefreshState.progress).coerceIn(0f, 1f)

            PullToRefreshContainer(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
                state = pullToRefreshState,
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (state.value) {
                    is MainScreenState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center)
                        )
                    }

                    is MainScreenState.Error -> {
                        Text(
                            text = (state.value as MainScreenState.Error).error.message.toString(),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                        )
                    }

                    is MainScreenState.Content -> {
                        GetFilmsPaging(
                            navController,
                            (state.value as MainScreenState.Content).data.collectAsLazyPagingItems()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GetFilmsPaging(navController: NavHostController, movies: LazyPagingItems<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = movies.itemCount,
            key = movies.itemKey(),
            contentType = movies.itemContentType()
        ) { index ->
            val item = movies[index]
            if (item != null) {
                FilmCard(movie = item, navController)
            }
        }
        item {
            if (movies.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
        item {
            if (movies.loadState.append is LoadState.Error) {
                Timber.tag("error").e("Paging Error State")
            }
        }
    }
}