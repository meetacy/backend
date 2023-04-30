package app.meetacy.backend.types.serialization
import app.meetacy.backend.types.Optional
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

// todo fixed by Y9San9
/**
 * Optional.Undefined is never being encoded in JSON
 */
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
        override val descriptor: SerialDescriptor = object : SerialDescriptor by subSerializer.descriptor {
            override val serialName = "OptionalSerializer"
            override val isNullable = true
        }
        override fun deserialize(decoder: Decoder): OptionalSerializable<T> {
            val value = decoder.decodeSerializableValue(subSerializer)
            return Present(value)
        }
        override fun serialize(encoder: Encoder, value: OptionalSerializable<T>) {
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
fun <T> Optional<T>.serializable(): OptionalSerializable<T> = when (this) {
    is Optional.Present -> OptionalSerializable.Present(value)
    is Optional.Undefined -> OptionalSerializable.Undefined
}
