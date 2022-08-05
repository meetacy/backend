package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.AccessToken
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
@JvmInline
value class AccessTokenSerializable(private val string: String) {
    fun type() = AccessToken(string)
}

fun AccessToken.serializable() = AccessTokenSerializable(string)
