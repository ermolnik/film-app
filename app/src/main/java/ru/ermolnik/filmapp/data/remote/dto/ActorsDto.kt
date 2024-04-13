package ru.ermolnik.filmapp.data.remote.dto

import ru.ermolnik.filmapp.domain.model.Actor


data class ActorsDto(val cast: List<ActorDto>)

fun List<ActorDto>.toDomain(): List<Actor> {
    return this.map { it.toDomain() }
}