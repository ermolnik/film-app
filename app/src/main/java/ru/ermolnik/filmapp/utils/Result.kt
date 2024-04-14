package ru.ermolnik.filmapp.utils

import timber.log.Timber
import java.util.concurrent.CancellationException


sealed class Result<out S, out E> {
    data class Success<out S>(val data: S) : Result<S, Nothing>()
    data class Error<out E>(val error: E) : Result<Nothing, E>()
}

inline fun <S, E> Result<S, E>.doOnSuccess(block: (S) -> Unit): Result<S, E> {
    if (this is Result.Success) {
        block(this.data)
    }
    return this
}

inline fun <S, E> Result<S, E>.doOnError(block: (E) -> Unit): Result<S, E> {
    if (this is Result.Error) {
        Timber.e("Result.doOnError: ".plus(this.error.toString()))
        block(this.error)
    }
    return this
}

inline fun <S, R> S.runOperationCatching(block: S.() -> R): Result<R, Throwable> {
    return try {
        Result.Success(block())
    } catch (e: CancellationException) {
        Timber.e("runOperationCatching: ".plus(e.toString()))
        throw e
    } catch (e: Throwable) {
        Timber.e("runOperationCatching: ".plus(e.toString()))
        Result.Error(e)
    }
}