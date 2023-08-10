package app.meetacy.backend.types.serializable.access

import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.serializable.access.AccessToken as AccessTokenSerializable

fun AccessTokenSerializable.type() = AccessToken(string)
fun AccessToken.serializable() = AccessTokenSerializable(string)
