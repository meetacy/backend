package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.AccessToken
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class AccessTokenSerializable(private val string: String) {
    fun type() = AccessToken(string)
}

fun AccessToken.serializable() = AccessIdentitySerializable(string)
