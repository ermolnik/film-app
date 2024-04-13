package ru.ermolnik.filmapp.data.remote.dto

import ru.ermolnik.filmapp.data.local.entity.MovieEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.ermolnik.filmapp.domain.model.Movie

private const val BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w500/"

@Serializable
data class MovieDto(
    val id: Int,
    @SerializedName("title") val name: String,
    @SerializedName("poster_path") val image: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("overview") val description: String,
    @SerializedName("genre_ids") val genres: List<Int>,
    val age: String? = "0",
)

fun MovieDto.toDomain() = Movie(
    id = id,
    name = name,
    image = BASE_URL_IMAGE + image,
    rating = rating.div(2),
    genres = genres,
    description = description,
    age = age ?: "0"
)

fun MovieDto.toEntity() = MovieEntity(
    id = id,
    name = name,
    image = BASE_URL_IMAGE + image,
    rating = rating.div(2),
    description = description,
    age = age ?: "0"
)
