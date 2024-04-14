package ru.ermolnik.filmapp.utils

import java.util.concurrent.CancellationException

sealed class Result<out S, out E> {

    data class Success<out S>(val result: S) : Result<S, Nothing>()

    data class Error<out E>(val result: E) : Result<Nothing, E>()
}

typealias VoidResult<E> = Result<Unit, E>

fun <S> Result<S, Throwable>.getOrThrow(): S =
    when (this) {
        is Result.Success -> this.result
        is Result.Error -> throw this.result
    }

inline fun <S, E, R> Result<S, E>.mapSuccess(block: (S) -> R): Result<R, E> =
    when (this) {
        is Result.Success -> Result.Success(result = block(this.result))
        is Result.Error -> Result.Error(result = this.result)
    }

inline fun <S, E, R> Result<S, E>.mapError(block: (E) -> R): Result<S, R> =
    when (this) {
        is Result.Success -> Result.Success(result = this.result)
        is Result.Error -> Result.Error(result = block(this.result))
    }

inline fun <S, E, R> Result<S, E>.mapNestedSuccess(
    block: (S) -> Result<R, E>,
): Result<R, E> =
    when (this) {
        is Result.Success -> block(this.result)
        is Result.Error -> Result.Error(result = this.result)
    }

inline fun <S, E> Result<S, E>.doOnSuccess(block: (S) -> Unit): Result<S, E> {
    if (this is Result.Success) {
        block(this.result)
    }
    return this
}

inline fun <S, E> Result<S, E>.doOnError(block: (E) -> Unit): Result<S, E> {
    if (this is Result.Error) {
        block(this.result)
    }
    return this
}

inline fun <S, R> S.runOperationCatching(block: S.() -> R): Result<R, Throwable> {
    return try {
        Result.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.Error(e)
    }
}

inline fun <reified S, reified E> List<Result<S, E>>.toSuccessOrErrorList(): Result<List<S>, List<E>> {
    var successResults: MutableList<S>? = null
    var errorResults: MutableList<E>? = null

    var hasErrors = false

    for (item: Result<S, E> in this) {
        when {
            ((item is Result.Success) && !hasErrors) -> {
                if (successResults == null) {
                    successResults = mutableListOf()
                }

                successResults.add(item.result)
            }
            (item is Result.Error) -> {
                hasErrors = true

                if (errorResults == null) {
                    errorResults = mutableListOf()
                }

                errorResults.add(item.result)
            }
        }
    }

    return if (errorResults != null) {
        Result.Error(result = errorResults)
    } else {
        Result.Success(result = successResults.orEmpty())
    }
}