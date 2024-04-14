package ru.ermolnik.filmapp.ui.details

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import ru.ermolnik.filmapp.R
import ru.ermolnik.filmapp.domain.model.Actor
import ru.ermolnik.filmapp.shared_ui.AgeBar
import ru.ermolnik.filmapp.shared_ui.CategoriesItem
import ru.ermolnik.filmapp.shared_ui.CustomRatingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsScreen(
    detailsScreenViewModel: DetailsViewModel,
    navController: NavHostController,
) {

    val state = detailsScreenViewModel.movieDetails.collectAsState()
    val configuration = LocalConfiguration.current

    Box(modifier = Modifier.fillMaxSize()) {
        when (state.value) {
            is DetailsScreenState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }

            is DetailsScreenState.Error -> {
                Text(
                    text = (state.value as DetailsScreenState.Error).error.message.toString(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is DetailsScreenState.Content -> {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 16.dp),
                    sheetPeekHeight = configuration.screenHeightDp.dp - (configuration.screenHeightDp.dp / 3),
                    sheetContent = {
                        when (state.value) {
                            is DetailsScreenState.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            is DetailsScreenState.Error -> {
                                Text(
                                    text = (state.value as DetailsScreenState.Error).error.message.toString(),
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }

                            is DetailsScreenState.Content -> {
                                val film = (state.value as DetailsScreenState.Content).data
                                Column(
                                    verticalArrangement = Arrangement.Bottom,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .verticalScroll(rememberScrollState())
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 30.dp, start = 20.dp, end = 20.dp),
                                    ) {
                                        CategoriesItem(category = film.genre, onclick = {})
                                        Text(
                                            text = film.datePublication,
                                            Modifier
                                                .padding(start = 8.dp)
                                                .align(Alignment.CenterVertically),
                                            fontSize = 12.sp,
                                            color = Color.Black
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 20.dp, end = 20.dp, bottom = 8.dp),
                                    ) {
                                        Text(
                                            text = film.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .padding(bottom = 8.dp, top = 16.dp)
                                                .align(Alignment.Bottom),
                                        )
                                        AgeBar(age = film.age, 50f)
                                    }
                                    Box(
                                        modifier = Modifier.padding(
                                            bottom = 20.dp,
                                            start = 20.dp
                                        )
                                    ) {
                                        film.rating?.let { CustomRatingView(rating = it) }
                                    }
                                    Text(
                                        text = film.description,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(horizontal = 20.dp)
                                    )
                                    Text(
                                        text = "Актеры",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(bottom = 8.dp, top = 16.dp, start = 20.dp)
                                            .align(Alignment.Start),
                                    )
                                    LazyRow(
                                        contentPadding = PaddingValues(
                                            horizontal = 20.dp, vertical = 18.dp
                                        ),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    ) {
                                        items(film.actors) {
                                            ActorItem(it)
                                        }
                                    }
                                }
                            }
                        }
                    }) {
                    AsyncImage(
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth(),
                        model = "https://image.tmdb.org/t/p/w500/${(state.value as DetailsScreenState.Content).data.image}",
                        contentDescription = null,
                    )
                }
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    }
}

@Composable
fun ActorItem(actor: Actor) {
    Column {
        SubcomposeAsyncImage(
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .height(200.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(20.dp)),
            model = "https://image.tmdb.org/t/p/w500/$actor.photo",
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.test_image),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = ""
                )
            },
            contentDescription = null,
        )
        Text(text = actor.name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}