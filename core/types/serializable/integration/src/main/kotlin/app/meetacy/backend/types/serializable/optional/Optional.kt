package app.meetacy.backend.types.serializable.optional

import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.optional.Optional as OptionalSerializable

fun <T> OptionalSerializable<T>.type(): Optional<T> = serialization {
    when (this) {
        is OptionalSerializable.Present -> Optional.Present(value)
        is OptionalSerializable.Undefined -> Optional.Undefined
    }
}
fun <T> Optional<T>.serializable(): OptionalSerializable<T> = when (this) {
    is Optional.Present -> OptionalSerializable.Present(value)
    is Optional.Undefined -> OptionalSerializable.Undefined
}
