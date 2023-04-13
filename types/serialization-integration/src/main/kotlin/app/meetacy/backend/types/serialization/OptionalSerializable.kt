package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.Optional
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = OptionalSerializable.Serializer::class)
sealed interface OptionalSerializable<out T> {
    val value: T? get() = null

    fun type(): Optional<T> = when (this) {
        is Present -> Optional.Present(value)
        is Undefined -> Optional.Undefined
    }

    class Present<T>(override val value: T) : OptionalSerializable<T>
    object Undefined : OptionalSerializable<Nothing>

    class Serializer<T>(
        private val subSerializer: KSerializer<T>
    ) : KSerializer<OptionalSerializable<T>> {
        override val descriptor = SerialDescriptor(
            serialName = "EditParam" + subSerializer.descriptor.serialName,
            original = subSerializer.descriptor
        )

        override fun deserialize(decoder: Decoder): OptionalSerializable<T> {
            return Present(decoder.decodeSerializableValue(subSerializer))
        }

        override fun serialize(encoder: Encoder, value: OptionalSerializable<T>) {
            encoder.encodeNullableSerializableValue(subSerializer, value.value)
        }
    }
}

fun <T> Optional<T>.serializable(): OptionalSerializable<T> = when (this) {
    is Optional.Present -> OptionalSerializable.Present(value)
    is Optional.Undefined -> OptionalSerializable.Undefined
}
