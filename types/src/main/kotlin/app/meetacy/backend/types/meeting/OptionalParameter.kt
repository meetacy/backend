package app.meetacy.backend.types.meeting

sealed interface OptionalParameter<out T> {
    val value: T? get() = null

    class Present<T>(override val value: T) : OptionalParameter<T>
    object Undefined : OptionalParameter<Nothing>
}