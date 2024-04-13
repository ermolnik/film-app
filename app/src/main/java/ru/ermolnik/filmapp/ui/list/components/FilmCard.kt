package ru.ermolnik.filmapp.ui.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import ru.ermolnik.filmapp.ui.navigation.Routes
import ru.ermolnik.filmapp.R
import ru.ermolnik.filmapp.domain.model.Movie
import ru.ermolnik.filmapp.ui.main_components.AgeBar
import ru.ermolnik.filmapp.ui.main_components.CustomRatingView

@Composable
fun FilmCard(movie: Movie, navController: NavHostController) {
    Box(modifier = Modifier
        .padding(20.dp)
        .fillMaxSize()
        .background(Color.Transparent)
        .clickable {
            navController.navigate(route = "${Routes.DetailsScreen.routes}/${movie.id}")
        }) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            modifier = Modifier.background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        ) {
            Column {
                SubcomposeAsyncImage(
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    model = "https://image.tmdb.org/t/p/w500/${movie.image}",
                    loading = {
                        Box(
                            modifier = Modifier.height(220.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.test_image),
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.height(220.dp),
                            contentDescription = ""
                        )
                    },
                    contentDescription = null,
                )
                Text(
                    text = movie.name,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.height(100.dp),
                    text = movie.description,
                    color = Color.Black,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .padding(top = 15.dp, end = 10.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomRatingView(modifier = Modifier.fillMaxHeight(), movie.rating, 3.dp)
                    AgeBar(movie.age)
                }
            }
        }
    }
}