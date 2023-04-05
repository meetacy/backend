package app.meetacy.backend.stdlib.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <T, R> Flow<T>.chunked(
    chunkSize: Int,
    crossinline transform: suspend (List<T>) -> R
): Flow<R> = flow {
    require(chunkSize > 0) { "Chunk size should be more than zero, but was $chunkSize" }

    val buffer = mutableListOf<T>()

    collect { value ->
        buffer.add(value)
        if (buffer.size == chunkSize) {
            emit(transform(buffer))
            buffer.clear()
        }
    }

    if (buffer.isNotEmpty()) emit(transform(buffer))
}

fun <T> Flow<T>.chunked(chunkSize: Int): Flow<List<T>> = chunked(chunkSize) { it }
