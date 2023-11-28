package app.meetacy.backend.types.serializable

import kotlinx.serialization.SerializationException

inline fun <T> serialization(block: () -> T): T {
    try {
        return block()
    } catch (exception: Throwable) {
        throw SerializationException(exception.message, exception)
    }
}
