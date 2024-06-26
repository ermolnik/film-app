package ru.ermolnik.filmapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.ermolnik.filmapp.domain.model.Genre
import ru.ermolnik.filmapp.domain.model.MovieDetails

private const val BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w500/"

@Serializable
data class MovieDetailsDto(
    @SerializedName("original_title") val name: String,
    @SerializedName("poster_path") val image: String,
    @SerializedName("release_date") val date_publication: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("overview") val description: String,
    @SerializedName("genres") val genre: List<GenreDto>,
    val age: String?,
    val actors: List<ActorDto>?
)

fun MovieDetailsDto.toDomain() = MovieDetails(
    name = name,
    image = BASE_URL_IMAGE + image,
    datePublication = date_publication,
    rating = rating.div(2),
    description = description,
    age = age ?: "0",
    genre = if (genre.isEmpty()) {
        Genre(0, "Genre")
    } else {
        genre[0].toDomain()
    },
    actors = (actors?.toDomain() ?: emptyList())
)