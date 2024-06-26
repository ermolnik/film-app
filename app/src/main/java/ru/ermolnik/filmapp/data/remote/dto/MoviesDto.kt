package ru.ermolnik.filmapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MoviesDto(
    val results: List<MovieDto>
)

fun MoviesDto.toDomain() = results.map { it.toDomain() }