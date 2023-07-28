@file:Suppress("OPT_IN_OVERRIDE")

package app.meetacy.backend.types.serializable.optional

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Optional.Undefined is never being encoded in JSON
 */
@Serializable(with = Optional.Serializer::class)
sealed interface Optional<out T> {
    val value: T? get() = null
    class Present<T>(override val value: T) : Optional<T>
    object Undefined : Optional<Nothing>

    class Serializer<T>(
        private val subSerializer: KSerializer<T>
    ) : KSerializer<Optional<T>> {

        override val descriptor: SerialDescriptor = object : SerialDescriptor by subSerializer.descriptor {
            override val serialName = "OptionalSerializer"
            override val isNullable = true
        }

        override fun deserialize(decoder: Decoder): Optional<T> {
            val value = decoder.decodeSerializableValue(subSerializer)
            return Present(value)
        }

        override fun serialize(encoder: Encoder, value: Optional<T>) {
            if (value !is Present) {
                error(
                    message = "Only 'Present' values can be encoded, " +
                            "please consider to use 'Undefined' as default " +
                            "value in order to prevent it from encoding"
                )
            }

            encoder.encodeSerializableValue(subSerializer, value.value)
        }
    }
}
