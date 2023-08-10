package app.meetacy.backend.types.serializable.access

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.serializable.access.AccessHash as AccessHashSerializable

fun AccessHashSerializable.type() = AccessHash(string)
fun AccessHash.serializable() = AccessHashSerializable(string)
