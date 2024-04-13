package ru.ermolnik.filmapp.data.remote.dto

data class GenresDto(val genres: List<GenreDto>)

fun GenresDto.toDomain() = this.genres.map { it.toDomain() }