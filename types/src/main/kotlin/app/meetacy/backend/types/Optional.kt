package app.meetacy.backend.types

sealed interface Optional<out T> {
    val value: T? get() = null

    class Present<T>(override val value: T) : Optional<T>
    object Undefined : Optional<Nothing>
}

inline fun <T, R> Optional<T>.map(transform: (T) -> R): Optional<R> =
    when (this) {
        is Optional.Present -> Optional.Present(transform(value))
        is Optional.Undefined -> Optional.Undefined
    }

inline fun <T, R> Optional<T>.ifPresent(block: (T) -> R): R? = when (this) {
    is Optional.Present -> block(value)
    is Optional.Undefined -> null
}

inline fun <T, R> Optional<T>.ifUndefined(block: () -> R): R? = when (this) {
    is Optional.Present -> null
    is Optional.Undefined -> block()
}
