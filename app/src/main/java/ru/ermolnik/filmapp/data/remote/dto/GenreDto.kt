package ru.ermolnik.filmapp.data.remote.dto

import ru.ermolnik.filmapp.domain.model.Genre

data class GenreDto(val id: Int, val name: String)

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}