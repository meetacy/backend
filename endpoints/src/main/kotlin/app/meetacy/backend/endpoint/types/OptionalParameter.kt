package app.meetacy.backend.endpoint.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = OptionalParameter.Serializer::class)
sealed interface OptionalParameter<out T> {
    val value: T? get() = null

    class Present<T>(override val value: T) : OptionalParameter<T>
    object Undefined : OptionalParameter<Nothing>

    class Serializer<T>(
        private val subSerializer: KSerializer<T>
    ) : KSerializer<OptionalParameter<T>> {
        override val descriptor = SerialDescriptor(
            serialName = "EditParam" + subSerializer.descriptor.serialName,
            original = subSerializer.descriptor
        )

        override fun deserialize(decoder: Decoder): OptionalParameter<T> {
            return Present(decoder.decodeSerializableValue(subSerializer))
        }

        override fun serialize(encoder: Encoder, value: OptionalParameter<T>) {
            encoder.encodeNullableSerializableValue(subSerializer, value.value)
        }
    }
}