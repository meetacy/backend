package app.meetacy.backend.types.serializable.access

import app.meetacy.backend.types.access.AccessToken
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class AccessTokenSerializable(private val string: String) {
    fun type() = AccessToken(string)
}

fun AccessToken.serializable() = AccessTokenSerializable(string)
