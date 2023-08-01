package app.meetacy.backend.types.serialization.access

import app.meetacy.backend.types.access.AccessToken

@Serializable
@JvmInline
value class AccessTokenSerializable(private val string: String) {
    fun type() = AccessToken(string)
}

fun AccessToken.serializable() = AccessTokenSerializable(string)
