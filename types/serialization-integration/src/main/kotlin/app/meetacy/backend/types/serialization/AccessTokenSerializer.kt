package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object AccessTokenSerializer : KSerializer<AccessToken> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "access_token",
        kind = PrimitiveKind.STRING
    )

    override fun deserialize(decoder: Decoder): AccessToken =
        AccessToken(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: AccessToken) =
        encoder.encodeString(value.string)
}
